package com.example.oauth.user;

import com.example.oauth.fingerprint.IpGeolocationClient;
import com.example.oauth.model.FingerPrint;
import com.example.oauth.model.IpGeolocationResponse;
import com.example.oauth.model.User;
import com.example.oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;

import static com.example.oauth.utils.RequestUtils.USER_AGENT_HEADER;
import static com.example.oauth.utils.RequestUtils.extractIp;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final IpGeolocationClient client;
    private final Parser parser;

    public void saveUser(UserDTO userDTO, HttpServletRequest request) {
        userDTO.encodePassword(bCryptPasswordEncoder);
        User user = User.map(userDTO);
        addFingerPrint(user, request);
        userRepository.save(user);
    }

    private void addFingerPrint(User user, HttpServletRequest request) {
        IpGeolocationResponse ipGeolocationResponse = client.findById(extractIp(request));
        Client clientUa = parser.parse(request.getHeader(USER_AGENT_HEADER));
        user.addFingerPrint(FingerPrint.create(ipGeolocationResponse, clientUa));
    }
}
