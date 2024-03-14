package com.pokemon.newsfeed.service;

import com.pokemon.newsfeed.dto.requestDto.LoginRequestDto;
import com.pokemon.newsfeed.dto.requestDto.SignupRequestDto;
import com.pokemon.newsfeed.dto.requestDto.UserUpdateDto;
import com.pokemon.newsfeed.dto.responseDto.LoginResponseDto;
import com.pokemon.newsfeed.dto.responseDto.ProfileResponseDto;
import com.pokemon.newsfeed.entity.Board;
import com.pokemon.newsfeed.entity.Comment;
import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.entity.UserRoleEnum;
import com.pokemon.newsfeed.repository.BoardRepository;
import com.pokemon.newsfeed.repository.CommentRepository;
import com.pokemon.newsfeed.repository.UserRepository;
import com.pokemon.newsfeed.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void signup(SignupRequestDto requestDto) {
        // 회원가입 시 중복이 허용되면 안되는 값들은 전부 DB에서 조회 후 비교하여 중복 여부를 판단해야 합니다.
        // 회원 중복 체크
        String userId = requestDto.getUserId();
        Optional<User> checkUserId = userRepository.findByUserId(userId);
        if (checkUserId.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        // 받아온 비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        // email 중복 체크
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        // 이름은 중복될 수 있기 때문에 따로 검사하지 않습니다.
        String name = requestDto.getName();

        // role은 관리자가 없어 USER를 그대로 담아줍니다.
        UserRoleEnum role = UserRoleEnum.USER;

        // 가져온 데이터 검증이 완료된 사용자 등록
        User user = new User(userId, password, email, name, role);
        userRepository.save(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        Optional<User> user = userRepository.findByUserId(requestDto.getUserId());
        // 받아온 입력 값 중에서 id를 이용하여 DB에서 동일한 id를 찾아서 user에 담습니다.
        if (user.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        // user의 데이터가 없다면
        if (!passwordEncoder.matches(requestDto.getPassword(), user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
        // DB에서 가져온 비밀번호와 입력한 비밀번호가 다를 경우

        String token = jwtUtil.createToken(user.get().getUserId(), UserRoleEnum.USER);
        // jwtUtil에 만들어둔 토큰 생성 메서드를 이용하여 토큰을 생성하기 위해
        // DB에서 가져온 아이디와 role을 createToken으로 보내 토큰을 생성하고 token 변수에 담습니다.

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .userId(user.get().getUserId())
                .password(user.get().getPassword())
                .email(user.get().getEmail())
                .name(user.get().getName())
                .token(token)
                .build();
        // 로그인이 성공적으로 이루어진다면 로그인 된 유저의 정보를 확인하기 위해 ResponseDto를 이용하여
        // DB에서 가져온 유저의 정보를 하나씩 담아 반환합니다.

        return responseDto;
    }

    @Override
    public void deleteUser(User user) {
        User findUser = findUser(findUser(user));
        if(!user.getUserNum().equals(findUser.getUserNum())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
        List<Board> userBoards = boardRepository.findByUser(user);
        for(Board board : userBoards) {
            List<Comment> boardComments = commentRepository.findByBoard(board);
            commentRepository.deleteAll(boardComments);
        }
        boardRepository.deleteAll(userBoards);
        userRepository.delete(user);
    }

    @Override
    public ProfileResponseDto getProfile(Long userNum) {
        User user = userRepository.findById(userNum).orElseThrow(() -> new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        return new ProfileResponseDto(user.getName(), user.getUserId(), user.getEmail(), user.getPassword());
    }

    @Override
    public ProfileResponseDto updateProfile(Long userNum, UserUpdateDto request) {
        User user = userRepository.findById(userNum).orElseThrow(() -> new IllegalArgumentException(" 계정 정보가 일치하지 않습니다."));
        // 비밀번호 확인
        // todo: RuntimeException인지 다른 Exception인지.. 같이 고민해보기
//        System.out.println(passwordEncoder.equals());
//        System.out.println(passwordEncoder.matches());
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("패스워드가 일치하지 않습니다.");
        }

        String changedPassword = passwordEncoder.encode(request.getPassword());

        user.updateProfile(request.getName(), request.getUserId(), request.getEmail(), changedPassword);
        return new ProfileResponseDto(user.getName(), user.getUserId(), user.getEmail(), changedPassword);
    }

    private User findUser(User user) {
        return userRepository.findById(user.getUserNum()).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
    }

}
