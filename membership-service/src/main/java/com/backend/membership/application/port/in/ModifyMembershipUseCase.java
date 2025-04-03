package com.backend.membership.application.port.in;

import com.backend.common.UseCase;
import com.backend.membership.domain.Membership;

@UseCase
public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
