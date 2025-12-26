package com.xenialsoft.api.core.auth.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public final class CustomUserDetails implements UserDetails, OAuth2User, CredentialsContainer {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 사용자 고유 식별자
     */
    private final String id;

    /**
     * 사용자의 로그인 식별자입니다.
     */
    private final String username;

    /**
     * 사용자 계정의 비밀번호입니다.
     */
    private String password;

    /**
     * 사용자의 역할 정보
     */
    private final Set<GrantedAuthority> authorities;

    /**
     * 사용자 속성 정보(OAuth2User)
     */
    private final Map<String, Object> attributes;

    /**
     * 계정의 만료 여부를 나타냅니다.
     */
    private final boolean accountNonExpired;

    /**
     * 계정의 잠금 상태를 나타냅니다.
     */
    private final boolean accountNonLocked;

    /**
     * 계정의 비밀번호 만료 여부를 나타냅니다.
     */
    private final boolean credentialsNonExpired;

    /**
     * 계정의 활성화 상태를 나타냅니다.
     */
    private final boolean enabled;

    public CustomUserDetails(String id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this(id, username, password, true, true, true, true, authorities, new HashMap<>());
    }

    public CustomUserDetails(String id, String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attributes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(new HashSet<>(authorities));
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomUserDetails user) {
            return this.id.equals(user.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    public static CustomUserBuilder builder() {
        return new CustomUserBuilder();
    }

    public static final class CustomUserBuilder {

        private String id;

        private String username;

        private String password;

        private List<GrantedAuthority> authorities = new ArrayList<>();

        private Map<String, Object> attributes = new HashMap<>();

        private boolean accountExpired;

        private boolean accountLocked;

        private boolean credentialsExpired;

        private boolean disabled;

        private CustomUserBuilder() {
        }

        public CustomUserBuilder id(String id) {
            Assert.notNull(id, "id cannot be null");
            this.id = id;
            return this;
        }

        public CustomUserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public CustomUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CustomUserBuilder authorities(GrantedAuthority... authorities) {
            return authorities(Arrays.asList(authorities));
        }

        public CustomUserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        public CustomUserBuilder attributes(Map<String, Object> attributes) {
            Assert.notNull(attributes, "attributes cannot be null");
            this.attributes = attributes;
            return this;
        }

        public CustomUserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public CustomUserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public CustomUserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public CustomUserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public CustomUserDetails build() {
            return new CustomUserDetails(this.id, this.username, this.password, !this.disabled, !this.accountExpired,
                    !this.credentialsExpired, !this.accountLocked, this.authorities, this.attributes);
        }

    }

}
