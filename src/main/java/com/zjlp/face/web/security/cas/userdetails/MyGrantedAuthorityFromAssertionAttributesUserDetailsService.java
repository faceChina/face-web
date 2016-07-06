/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zjlp.face.web.security.cas.userdetails;

import java.util.ArrayList;
import java.util.List;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.server.user.user.business.UserBusiness;

/**
 * Populates the {@link org.springframework.security.core.GrantedAuthority}s for a user by reading a list of attributes that were returned as
 * part of the CAS response.  Each attribute is read and each value of the attribute is turned into a GrantedAuthority.  If the attribute has no
 * value then its not added.
 *
 * @author Scott Battaglia
 * @since 3.0
 */
public final class MyGrantedAuthorityFromAssertionAttributesUserDetailsService extends AbstractCasAssertionUserDetailsService {

    private static final String NON_EXISTENT_PASSWORD_VALUE = "NO_PASSWORD";

    private final String[] attributes;

    private boolean convertToUpperCase = true;
    
    @Autowired
	private UserBusiness userBussiness;

	public MyGrantedAuthorityFromAssertionAttributesUserDetailsService(final String[] attributes) {
        Assert.notNull(attributes, "attributes cannot be null.");
        Assert.isTrue(attributes.length > 0, "At least one attribute is required to retrieve roles from.");
        this.attributes = attributes;
    }

    @Override
    protected UserDetails loadUserDetails(final Assertion assertion) {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        
       UserInfo userInfo = userBussiness.getUserInfo(assertion.getPrincipal().getName());

        for (final String attribute : this.attributes) {
            final Object value = assertion.getPrincipal().getAttributes().get(attribute);

            if (value == null) {
                continue;
            }
            if (value instanceof List) {
                final List<?> list = (List<?>) value;
                for (final Object o : list) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(this.convertToUpperCase ? o.toString().toUpperCase().trim() : o.toString()));
                }
            }else if(value instanceof String && String.valueOf(value).startsWith("[") && String.valueOf(value).endsWith("]")
            		&& String.valueOf(value).indexOf(",")!= -1){
            	StringBuilder sb = new StringBuilder(value.toString());
            	sb.delete(0, 1).delete(sb.length()-1, sb.length());
               	final String[] objs = String.valueOf(sb.toString()).split(",");
                for (final Object o : objs) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(this.convertToUpperCase ? o.toString().toUpperCase().trim() : o.toString()));
                }
            }
            else {
                grantedAuthorities.add(new SimpleGrantedAuthority(this.convertToUpperCase ? value.toString().toUpperCase().trim() : value.toString()));
            }

        }
		// 封装成spring security的user
        return new WgjUser(assertion.getPrincipal().getName(),
        		NON_EXISTENT_PASSWORD_VALUE, true, true, true, true, grantedAuthorities // 用户的权限
				, userInfo);
//        return new User(assertion.getPrincipal().getName(), NON_EXISTENT_PASSWORD_VALUE, true, true, true, true, grantedAuthorities);
    }

    /**
     * Converts the returned attribute values to uppercase values.
     *
     * @param convertToUpperCase true if it should convert, false otherwise.
     */
    public void setConvertToUpperCase(final boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }
    
    public void setUserBussiness(UserBusiness userBussiness) {
		this.userBussiness = userBussiness;
	}

}
