package com.jpadata.datajpa.repository;

import com.jpadata.datajpa.entity.Member;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecification {
    public static Specification<Member> teamName(final String teamName) {
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (StringUtils.isEmpty(teamName)) {
                    return null;
                }

                Join<Object, Object> t = root.join("team", JoinType.INNER);

                return builder.equal(t.get("name"), teamName);
            }
        };
    }

    public static Specification<Member> username(final String username) {
        return (Specification<Member>) (root, query, builder) ->
                builder.equal(root.get("username"), username);
    }
}