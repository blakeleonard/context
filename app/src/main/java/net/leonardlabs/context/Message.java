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
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package net.leonardlabs.context;

import java.util.Date;

class Message {

    private long id;
    private String uniqueId;
    private int encryptedId;
    private String email;
    private String name;
    private Date sentFromSenderAt;
    private Date receivedAt;
    private String status;
    private boolean incoming;
    private String hmac;
    private String body;

    Message () {
    }

    Message(long id, String uniqueId, int encryptedId, String email, String name, Date sentFromSenderAt, Date receivedAt, String status, boolean incoming, String hmac, String body) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.encryptedId = encryptedId;
        this.email = email;
        this.name = name;
        this.sentFromSenderAt = sentFromSenderAt;
        this.receivedAt = receivedAt;
        this.status = status;
        this.incoming = incoming;
        this.hmac = hmac;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(int encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSentFromSenderAt() {
        return sentFromSenderAt;
    }

    public void setSentFromSenderAt(Date sentFromSenderAt) {
        this.sentFromSenderAt = sentFromSenderAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
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
