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

public class Contact {

    private Long id;
    private String email;
    private String name;
    private String imageUri;
    private int currentSendMsgId;
    private int currentReceiveMsgId;

    public Contact(Long id, String email, String name, String imageUri, int currentSendMsgId, int currentReceiveMsgId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.imageUri = imageUri;
        this.currentSendMsgId = currentSendMsgId;
        this.currentReceiveMsgId = currentReceiveMsgId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getCurrentSendMsgId() {
        return currentSendMsgId;
    }

    public void setCurrentSendMsgId(int currentSendMsgId) {
        this.currentSendMsgId = currentSendMsgId;
    }

    public int getCurrentReceiveMsgId() {
        return currentReceiveMsgId;
    }

    public void setCurrentReceiveMsgId(int currentReceiveMsgId) {
        this.currentReceiveMsgId = currentReceiveMsgId;
    }
}
