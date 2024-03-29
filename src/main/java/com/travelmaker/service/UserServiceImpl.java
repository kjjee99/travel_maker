package com.travelmaker.service;

import com.travelmaker.config.AmazonS3ResourceStorage;
import com.travelmaker.dto.FileDetail;
import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.FollowRepository;
import com.travelmaker.repository.PostRepository;
import com.travelmaker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AmazonS3ResourceStorage amazonS3ResourceStorage;

    /* 회원가입 */
    @Override
    public boolean addUser(User user){
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity entity = UserEntity.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .password(encodedPassword)
                .phoneNumber(user.getPhoneNumber())
                .profileImg(user.getProfileImg())
                .build();

        // DB 저장
        UserEntity savedUser = repository.save(entity);

        if(savedUser.getUserId().isEmpty())    throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        return true;
    }

    /* 중복 아이디 확인 */
    @Override
    public boolean checkId(String id){
        Optional<UserEntity> entity = repository.findByUserId(id);
        // 아이디가 존재하는 경우 에러 발생
        if(entity.isPresent() && !entity.get().getUserId().isEmpty())
            throw new CustomException(ErrorCode.ID_EXISTS);
        return true;
    }

    /* 로그인 */
    @Override
    public String login(User user){
        String id = user.getId();
        String password = user.getPassword();
        // user object를 DB에서 가져오기
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(id)
                // ERROR 던지기
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity findUser = entity.get();
        // DB에 저장된 비밀번호
        String userPW = findUser.getPassword();
        // 비밀번호가 같지 않을 경우
        if(!passwordEncoder.matches(password, userPW)){
            // ERROR 던지기
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return findUser.getUserId();
    }

    /* 유저 정보 조회 */
    @Override
    public User searchUser(String userId){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity user = entity.get();

        // 팔로잉 숫자
        Optional<Integer> following = Optional.ofNullable(followRepository.countFollowingByUser(user.getIdx())
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));
        // 팔로워 숫자
        Optional<Integer> follower = Optional.ofNullable(followRepository.countFollowerByUser(user.getIdx())
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        User findUser = User.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImg(user.getProfileImg())
                .following(following.get())
                .follower(follower.get())
                .build();

        return findUser;
    }

    /* 유저 정보 수정 */
    @Override
    public boolean modifyUser(User user, MultipartFile image){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(user.getId())
                // userId가 없는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity findUser = entity.get();

        checkPassword(user.getPassword(), findUser.getPassword());

        String storedImg = "";
        // 이미지 저장하기
        if(!image.isEmpty()){
            FileDetail fileDetail = FileDetail.multipartOf(image);
            storedImg = amazonS3ResourceStorage.store(fileDetail.getPath(), image, fileDetail.getId());
        } else {
            storedImg = findUser.getProfileImg();
        }

        log.info(storedImg);

        // params ? not changed : changed
        // 공란이면 이미지 변경 없음
        String email = user.getEmail().isEmpty() ? findUser.getEmail() : user.getEmail();
        String phone_number = user.getPhoneNumber().isEmpty() ? findUser.getPhoneNumber() : user.getPhoneNumber();

        Optional<Integer> updatedUser = Optional.ofNullable(repository.updateUser(user.getId(), email, phone_number, storedImg)
                // 수정되지 않은 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        // TODO: 회원 정보 객체 전달
        return true;
    }

    /**
     *  비밀번호 확인
     * @param compare : 비교할 비밀번호
     * @param password : 원래 비밀번호
     * @return boolean
     *  */
    @Override
    public boolean checkPassword(String compare, String password){
        if(!passwordEncoder.matches(compare, password))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        return true;
    }

    /* 비밀번호 변경 */
    @Override
    public boolean modifyPass(User user, String newPassword){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(user.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        UserEntity findUser = entity.get();

        if(!passwordEncoder.matches(user.getPassword(), findUser.getPassword()))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        String encodedPassword = passwordEncoder.encode(newPassword);
        Optional<Integer> updatedUser = Optional.ofNullable(repository.updatePass(user.getId(), encodedPassword))
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));

        return true;
    }

    /* 회원 탈퇴 */
    public boolean deleteUser(String userId, String password){

        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity findUser = entity.get();
        if(!passwordEncoder.matches(password, findUser.getPassword()))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        // 게시글 삭제
        postRepository.deletePostsByUser(userId).orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));

        // 팔로워 삭제
        followRepository.removeFollows(findUser.getIdx()).orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));

        Optional<Integer> deletedUser = Optional.ofNullable(repository.deleteByUserId(findUser.getUserId())
                // 삭제되지 않은 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));
        return true;
    }

    /* 회원 검색 */
    @Override
    public List<UserEntity> searchUserByKeyword(String word, int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber, 6);
        List<UserEntity> list = repository.findByKeyword(word, pageRequest);
        // ERROR: 값이 존재하지 않을 때
        if(list.size() == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

        return list;
    }
}
