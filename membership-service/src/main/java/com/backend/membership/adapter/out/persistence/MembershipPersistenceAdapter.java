package com.backend.membership.adapter.out.persistence;

import com.backend.membership.application.port.out.FindMembershipPort;
import com.backend.membership.application.port.out.RegisterMembershipPort;
import com.backend.membership.domain.Membership;
import common.PersistenceAdapter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
            new MembershipJpaEntity(
                    membershipName.getNameValue(),
                    membershipEmail.getEmailValue(),
                    membershipAddress.getAddressValue(),
                    membershipIsValid.isValidValue(),
                    membershipIsCorp.isCorpValue()
            )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return membershipRepository.findById(Long.parseLong(membershipId.getMembershipId()))
                .orElseThrow(() -> new EntityNotFoundException("Membership not found with ID: " + membershipId.getMembershipId()));
    }
}
