package com.backend.membership.application.service;

import com.backend.common.UseCase;
import com.backend.membership.adapter.out.persistence.MembershipJpaEntity;
import com.backend.membership.adapter.out.persistence.MembershipMapper;
import com.backend.membership.application.port.in.ModifyMembershipCommand;
import com.backend.membership.application.port.in.ModifyMembershipUseCase;
import com.backend.membership.application.port.out.ModifyMembershipPort;
import com.backend.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUseCase{

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
