package com.backend.membership.adapter.in.web;

import com.backend.membership.application.port.in.FindMembershipCommand;
import com.backend.membership.application.port.in.FindMembershipUseCase;
import com.backend.membership.domain.Membership;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindMembershipControllerTest {

    @InjectMocks
    private FindMembershipController findMembershipController;

    @Mock
    private FindMembershipUseCase findMembershipUseCase;

    @Test
    public void testFindMembershipByMemberId_Success() {
        // 준비 (Arrange)
        String membershipId = "1";

        // 예상되는 커맨드 객체
        FindMembershipCommand expectedCommand = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        // Mock 응답 설정
        Membership mockResponse = Membership.generateMember(
                new Membership.MembershipId(membershipId),
                new Membership.MembershipName("testName"),
                new Membership.MembershipEmail("test@email.com"),
                new Membership.MembershipAddress("testAddress"),
                new Membership.MembershipIsValid(true),
                new Membership.MembershipIsCorp(false)
        );

        when(findMembershipUseCase.findMembership(any(FindMembershipCommand.class)))
                .thenReturn(mockResponse);

        // 실행 (Act)
        ResponseEntity<Membership> response = findMembershipController.findMembershipByMemberId(membershipId);

        // 검증 (Assert)
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Membership result = response.getBody();
        assertNotNull(result);
        assertEquals(membershipId, result.getMembershipId());
        assertEquals("testName", result.getName());
        assertEquals("test@email.com", result.getEmail());
        assertEquals("testAddress", result.getAddress());
        assertTrue(result.isValid());
        assertFalse(result.isCorp());

        // 서비스 호출 검증
        verify(findMembershipUseCase, times(1)).findMembership(any(FindMembershipCommand.class));
    }

    @Test
    public void testFindMembershipByMemberId_VerifyCommandCreation() {
        // 준비 (Arrange)
        String membershipId = "test123";

        // Mock 응답 설정
        when(findMembershipUseCase.findMembership(any(FindMembershipCommand.class)))
                .thenReturn(mock(Membership.class));

        // 실행 (Act)
        findMembershipController.findMembershipByMemberId(membershipId);

        // 검증 (Assert) - 정확한 커맨드 객체가 생성되어 전달되는지 검증
        verify(findMembershipUseCase).findMembership(argThat(command ->
                "test123".equals(command.getMembershipId())
        ));
    }

    @Test
    public void testFindMembershipByMemberId_CorporateMember() {
        // 준비 (Arrange)
        String membershipId = "corp123";

        // Mock 응답 설정 - 법인 회원
        Membership mockResponse = Membership.generateMember(
                new Membership.MembershipId(membershipId),
                new Membership.MembershipName("Corporation Name"),
                new Membership.MembershipEmail("corp@example.com"),
                new Membership.MembershipAddress("Corp Address"),
                new Membership.MembershipIsValid(true),
                new Membership.MembershipIsCorp(true)
        );

        when(findMembershipUseCase.findMembership(any(FindMembershipCommand.class)))
                .thenReturn(mockResponse);

        // 실행 (Act)
        ResponseEntity<Membership> response = findMembershipController.findMembershipByMemberId(membershipId);

        // 검증 (Assert)
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Membership result = response.getBody();
        assertNotNull(result);
        assertEquals(membershipId, result.getMembershipId());
        assertEquals("Corporation Name", result.getName());
        assertEquals("corp@example.com", result.getEmail());
        assertEquals("Corp Address", result.getAddress());
        assertTrue(result.isValid());
        assertTrue(result.isCorp());
    }
}