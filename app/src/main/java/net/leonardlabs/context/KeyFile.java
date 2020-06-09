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

public class KeyFile {

    private Long id;
    private String filename;
    private String passcode;

    public KeyFile(Long id, String filename, String passcode) {
        this.id = id;
        this.filename = filename;
        this.passcode = passcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
