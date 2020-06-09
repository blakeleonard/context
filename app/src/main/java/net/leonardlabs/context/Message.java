/*

Context: An encrypted messaging application.
Copyright (C) 2020  Blake Leonard

blake@leonardlabs.net

Leonard Labs
Blake Leonard
1209 Susan St.
Kearney, MO 64060

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
aLong with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package net.leonardlabs.context;

import java.util.Date;

public class Message {

    private Long id;
    private Long contactId;
    private String email;
    private String uniqueId;
    private Integer encryptedId;
    private boolean incoming;
    private Date sentAt;
    private Date receivedAt;
    private String hmac;
    private String body;

    public Message(Long id, Long contactId, String email, String uniqueId, Integer encryptedId, boolean incoming, Date sentAt, Date receivedAt, String hmac, String body) {
        this.id = id;
        this.contactId = contactId;
        this.email = email;
        this.uniqueId = uniqueId;
        this.encryptedId = encryptedId;
        this.incoming = incoming;
        this.sentAt = sentAt;
        this.receivedAt = receivedAt;
        this.hmac = hmac;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(Integer encryptedId) {
        this.encryptedId = encryptedId;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
