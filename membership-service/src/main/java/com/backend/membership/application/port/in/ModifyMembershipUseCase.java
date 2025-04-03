package com.backend.membership.application.port.in;

import com.backend.membership.domain.Membership;
import common.UseCase;

@UseCase
public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
