/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/


package org.apache.james.transport.matchers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.mailet.MailAddress;
import org.apache.mailet.base.test.FakeMail;
import org.apache.mailet.base.test.FakeMailContext;
import org.apache.mailet.base.test.FakeMatcherConfig;
import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Address;

public class SMTPAuthSuccessfulTest {

    private SMTPAuthSuccessful testee;

    @Before
    public void setUp() throws Exception {
        testee = new SMTPAuthSuccessful();
        testee.init(FakeMatcherConfig.builder()
        		.matcherName("matcherName")
            .mailetContext(FakeMailContext.defaultContext())
            .build());
    }

    @Test
    public void matchShouldReturnRecipientsWhenAuthUserAttributeIsPresent() throws Exception{
        /*
        Question 1

        Create a mail with the attribute SMTPAuthSuccessful.SMTP_AUTH_USER_ATTRIBUTE_NAME with a value, and a recipient

        Match this mail

        As a result, the recipient should be returned
         */
    	FakeMail fakeMail = FakeMail.builder()
    			.recipient(new MailAddress("thuyvinguyen.tvn@gmail.com"))
    			.attribute("org.apache.james.SMTPAuthUser", "true")
    			.build();
    	
    	Collection<MailAddress> result = testee.match(fakeMail);
    	
    	assertThat(result).contains(new MailAddress("thuyvinguyen.tvn@gmail.com"));
    	
    	
    	
    }

    @Test
    public void matchShouldNotReturnRecipientsWhenAuthUserAttributeIsAbsent() throws AddressException, MessagingException {
        /*
        Question 2

        Create a mail with only a recipient

        Match this mail

        As a result, the recipient should not be returned
         */
    	
    	FakeMail fakeMail = FakeMail.builder()
    			.recipient(new MailAddress("thuyvinguyen.tvn@gmail.com"))
    			.build();
    	
    	Collection<MailAddress> result = testee.match(fakeMail);
    	
    	assertThat(result).isNull();
    	
    	
    }

}
