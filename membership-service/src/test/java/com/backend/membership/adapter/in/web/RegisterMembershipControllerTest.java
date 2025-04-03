package com.backend.membership.adapter.in.web;

import com.backend.membership.application.port.in.RegisterMembershipCommand;
import com.backend.membership.application.port.in.RegisterMembershipUseCase;
import com.backend.membership.domain.Membership;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterMembershipControllerTest {

    @InjectMocks
    private RegisterMembershipController registerMembershipController;

    @Mock
    private RegisterMembershipUseCase registerMembershipUseCase;

    @Test
    public void testRegisterMembership_Corporate() {
        // 준비 (Arrange)
        RegisterMembershipRequest request = new RegisterMembershipRequest(
                "corpName",
                "corp@email.com",
                "corpAddress",
                true
        );

        // 예상되는 커맨드 객체 생성
        RegisterMembershipCommand expectedCommand = RegisterMembershipCommand.builder()
                .name("corpName")
                .email("corp@email.com")
                .address("corpAddress")
                .isValid(true)
                .isCorp(true)
                .build();

        // Mock 응답 설정
        Membership mockResponse = Membership.generateMember(
                new Membership.MembershipId("1"),
                new Membership.MembershipName("corpName"),
                new Membership.MembershipEmail("corp@email.com"),
                new Membership.MembershipAddress("corpAddress"),
                new Membership.MembershipIsValid(true),
                new Membership.MembershipIsCorp(true)
        );

        when(registerMembershipUseCase.registerMembership(any(RegisterMembershipCommand.class)))
                .thenReturn(mockResponse);

        // 실행 (Act)
        Membership result = registerMembershipController.registerMembership(request);

        // 검증 (Assert)
        assertNotNull(result);
        assertEquals("1", result.getMembershipId());
        assertEquals("corpName", result.getName());
        assertEquals("corp@email.com", result.getEmail());
        assertEquals("corpAddress", result.getAddress());
        assertTrue(result.isValid());
        assertTrue(result.isCorp());

        // 서비스 호출 검증
        verify(registerMembershipUseCase, times(1)).registerMembership(any(RegisterMembershipCommand.class));
    }

    @Test
    public void testRegisterMembership_Personal() {
        // 준비 (Arrange)
        RegisterMembershipRequest request = new RegisterMembershipRequest(
                "personalName",
                "personalAddress",
                "personal@email.com",
                false
        );

        // Mock 응답 설정
        Membership mockResponse = Membership.generateMember(
                new Membership.MembershipId("2"),
                new Membership.MembershipName("personalName"),
                new Membership.MembershipEmail("personal@email.com"),
                new Membership.MembershipAddress("personalAddress"),
                new Membership.MembershipIsValid(true),
                new Membership.MembershipIsCorp(false)
        );

        when(registerMembershipUseCase.registerMembership(any(RegisterMembershipCommand.class)))
                .thenReturn(mockResponse);

        // 실행 (Act)
        Membership result = registerMembershipController.registerMembership(request);

        // 검증 (Assert)
        assertNotNull(result);
        assertEquals("2", result.getMembershipId());
        assertEquals("personalName", result.getName());
        assertEquals("personal@email.com", result.getEmail());
        assertEquals("personalAddress", result.getAddress());
        assertTrue(result.isValid());
        assertFalse(result.isCorp());

        // 서비스 호출 검증 - 커맨드가 올바르게 생성되었는지 확인
        verify(registerMembershipUseCase).registerMembership(argThat(command ->
                command.getName().equals("personalName") &&
                        command.getEmail().equals("personal@email.com") &&
                        command.getAddress().equals("personalAddress") &&
                        command.isValid() &&
                        !command.isCorp()
        ));
    }
}