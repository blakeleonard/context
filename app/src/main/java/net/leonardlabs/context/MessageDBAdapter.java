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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;

class MessageDBAdapter {
    
    private static final String DATABASE_NAME = BaseActivity.messageDbFileName;
    private static final int DATABASE_VERSION = 1;
    private static final String CONTACT_TABLE = "contactTable";
    private static final String KEY_FILE_TABLE = "keyFileTable";
    private static final String MESSAGE_TABLE = "messageTable";
    
    private static final String KEY_CONTACT_ROW_ID = "_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_URI = "imageUri";
    private static final String KEY_CURRENT_SEND_MSG_ID = "currentSendMsgId";
    private static final String KEY_CURRENT_RECEIVE_MSG_ID = "currentReceiveMsgId";

    private static final int COL_CONTACT_ROW_ID = 0;
    private static final int COL_EMAIL = 1;
    private static final int COL_NAME = 2;
    private static final int COL_IMAGE_URI = 3;
    private static final int COL_CURRENT_SEND_MSG_ID = 4;
    private static final int COL_CURRENT_RECEIVE_MSG_ID = 5;

    private static final String KEY_KEY_FILE_ROW_ID = "_id";
    private static final String KEY_FILE_NAME = "filename";
    private static final String KEY_PASSCODE = "passcode";

    private static final int COL_KEY_FILE_ROW_ID = 0;
    private static final int COL_FILE_NAME = 1;
    private static final int COL_PASSCODE = 2;

    static final String KEY_MESSAGE_ROW_ID = "_id";
    static final String KEY_CONTACT_ID = "contactId";
    static final String KEY_UNIQUE_ID = "uniqueId";
    static final String KEY_ENCRYPTED_ID = "encryptedId";
    static final String KEY_INCOMING = "incoming";
    static final String KEY_SENT_AT = "sentAt";
    static final String KEY_RECEIVED_AT = "receivedAt";
    static final String KEY_HMAC = "hmac";
    static final String KEY_BODY = "body";

    static final int COL_MESSAGE_ROW_ID = 0;
    static final int COL_CONTACT_ID = 1;
    static final int COL_UNIQUE_ID = 2;
    static final int COL_ENCRYPTED_ID = 3;
    static final int COL_INCOMING = 4;
    static final int COL_SENT_AT = 5;
    static final int COL_RECEIVED_AT = 6;
    static final int COL_HMAC = 7;
    static final int COL_BODY = 8;
    
    private static final String[] ALL_CONTACT_KEYS = new String[] {KEY_CONTACT_ROW_ID, KEY_EMAIL, KEY_NAME, KEY_IMAGE_URI, KEY_CURRENT_SEND_MSG_ID, KEY_CURRENT_RECEIVE_MSG_ID};
    private static final String[] ALL_KEY_FILE_KEYS = new String[] {KEY_KEY_FILE_ROW_ID, KEY_FILE_NAME, KEY_PASSCODE};
    static final String[] ALL_MESSAGE_KEYS = new String[] {KEY_MESSAGE_ROW_ID, KEY_CONTACT_ID, KEY_UNIQUE_ID, KEY_ENCRYPTED_ID, KEY_INCOMING, KEY_SENT_AT, KEY_RECEIVED_AT, KEY_HMAC, KEY_BODY};
    
    private static final String CREATE_CONTACT_TABLE_SQL =
            "create table " + CONTACT_TABLE
                    + " (" + KEY_CONTACT_ROW_ID + " integer primary key autoincrement, "
                    + KEY_EMAIL + " string not null, "
                    + KEY_NAME + " string, "
                    + KEY_IMAGE_URI + " string, "
                    + KEY_CURRENT_SEND_MSG_ID + " integer not null, "
                    + KEY_CURRENT_RECEIVE_MSG_ID + " integer not null"
                    + ");";

    private static final String CREATE_KEY_FILE_TABLE_SQL =
            "create table " + KEY_FILE_TABLE
                    + " (" + KEY_KEY_FILE_ROW_ID + " integer primary key autoincrement, "
                    + KEY_FILE_NAME + " string, "
                    + KEY_PASSCODE + " string"
                    + ");";

    private static final String CREATE_MESSAGE_TABLE_SQL =
            " (" + KEY_MESSAGE_ROW_ID + " integer primary key autoincrement, "
                    + KEY_CONTACT_ID + " integer not null, "
                    + KEY_UNIQUE_ID + " string not null, "
                    + KEY_ENCRYPTED_ID + " integer, "
                    + KEY_INCOMING + " boolean not null, "
                    + KEY_SENT_AT + " date not null, "
                    + KEY_RECEIVED_AT + " date, "
                    + KEY_HMAC + " string not null, "
                    + KEY_BODY + " string not null"
                    + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    MessageDBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    MessageDBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    void close() {
        myDBHelper.close();
    }

    private long insertContact(Contact contact) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EMAIL, contact.getEmail());
        initialValues.put(KEY_NAME, contact.getName());
        initialValues.put(KEY_IMAGE_URI, contact.getImageUri());
        initialValues.put(KEY_CURRENT_SEND_MSG_ID, contact.getCurrentSendMsgId());
        initialValues.put(KEY_CURRENT_RECEIVE_MSG_ID, contact.getCurrentReceiveMsgId());
        return db.insert(CONTACT_TABLE, null, initialValues);
    }

    private long insertKeyFile(KeyFile keyFile) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FILE_NAME, keyFile.getFilename());
        initialValues.put(KEY_PASSCODE, keyFile.getPasscode());
        return db.insert(KEY_FILE_TABLE, null, initialValues);
    }

    private long insertMessage(Message message) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CONTACT_ID, message.getContactId());
        initialValues.put(KEY_UNIQUE_ID, message.getUniqueId());
        initialValues.put(KEY_ENCRYPTED_ID, message.getEncryptedId());
        initialValues.put(KEY_INCOMING, message.isIncoming());
        initialValues.put(KEY_SENT_AT, message.getSentAt().toString());
        initialValues.put(KEY_RECEIVED_AT, message.getReceivedAt().toString());
        initialValues.put(KEY_HMAC, message.getHmac());
        initialValues.put(KEY_BODY, message.getBody());
        return db.insert(MESSAGE_TABLE, null, initialValues);
    }

    private Contact getContact(long id) {
        String where = KEY_CONTACT_ROW_ID + "=" + id;
        Cursor c = 	db.query(true, CONTACT_TABLE, ALL_CONTACT_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            Contact contact = new Contact(c.getLong(COL_CONTACT_ROW_ID), c.getString(COL_EMAIL), c.getString(COL_NAME), c.getString(COL_IMAGE_URI),
                    c.getInt(COL_CURRENT_SEND_MSG_ID), c.getInt(COL_CURRENT_RECEIVE_MSG_ID));
            c.close();
            return contact;
        }
        return null;
    }

    private KeyFile getKeyFile(long id) {
        String where = KEY_KEY_FILE_ROW_ID + "=" + id;
        Cursor c = 	db.query(true, KEY_FILE_TABLE, ALL_KEY_FILE_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            KeyFile keyFile = new KeyFile(c.getLong(COL_KEY_FILE_ROW_ID), c.getString(COL_FILE_NAME), c.getString(COL_PASSCODE);
            c.close();
            return keyFile;
        }
        return null;
    }

    private Message getMessage(long id) {
        String where = KEY_MESSAGE_ROW_ID + "=" + id;
        Cursor c = 	db.query(true, MESSAGE_TABLE, ALL_MESSAGE_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            boolean incoming = Boolean.parseBoolean(c.getString(COL_INCOMING));
            Date sentAt = new Date(c.getString(COL_SENT_AT));
            Date receivedAt = new Date(c.getString(COL_RECEIVED_AT));
            Message message = new Message(c.getLong(COL_MESSAGE_ROW_ID), c.getLong(COL_CONTACT_ID), null, c.getString(COL_UNIQUE_ID), c.getInt(COL_ENCRYPTED_ID),
                    incoming, sentAt, receivedAt, c.getString(COL_HMAC), c.getString(COL_BODY));
            c.close();
            return message;
        }
        return null;
    }

    private boolean updateContact(Contact contact) {
        String where = KEY_CONTACT_ROW_ID + "=" + contact.getId();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_EMAIL, contact.getEmail());
        newValues.put(KEY_NAME, contact.getName());
        newValues.put(KEY_IMAGE_URI, contact.getImageUri());
        newValues.put(KEY_CURRENT_SEND_MSG_ID, contact.getCurrentSendMsgId());
        newValues.put(KEY_CURRENT_RECEIVE_MSG_ID, contact.getCurrentReceiveMsgId());
        return db.update(CONTACT_TABLE, newValues, where, null) != 0;
    }

    private boolean updateKeyFile(KeyFile keyFile) {
        String where = KEY_KEY_FILE_ROW_ID + "=" + keyFile.getId();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_FILE_NAME, keyFile.getFilename());
        newValues.put(KEY_PASSCODE, keyFile.getPasscode());
        return db.update(KEY_FILE_TABLE, newValues, where, null) != 0;
    }

    private boolean updateMessage(Message message) {
        String where = KEY_MESSAGE_ROW_ID + "=" + message.getId();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CONTACT_ID, message.getContactId());
        newValues.put(KEY_UNIQUE_ID, message.getUniqueId());
        newValues.put(KEY_ENCRYPTED_ID, message.getEncryptedId());
        newValues.put(KEY_INCOMING, message.isIncoming());
        newValues.put(KEY_SENT_AT, message.getSentAt().toString());
        newValues.put(KEY_RECEIVED_AT, message.getReceivedAt().toString());
        newValues.put(KEY_HMAC, message.getHmac());
        newValues.put(KEY_BODY, message.getBody());
        return db.update(MESSAGE_TABLE + String.valueOf(message.getContactId()), newValues, where, null) != 0;
    }
    
    private boolean deleteContact(long id) {
        String where = KEY_CONTACT_ROW_ID + "=" + id;
        return db.delete(CONTACT_TABLE, where, null) != 0;
    }

    private boolean deleteKeyFile(long id) {
        String where = KEY_KEY_FILE_ROW_ID + "=" + id;
        return db.delete(KEY_FILE_TABLE, where, null) != 0;
    }

    private boolean deleteMessage(long id) {
        String where = KEY_MESSAGE_ROW_ID + "=" + id;
        return db.delete(MESSAGE_TABLE, where, null) != 0;
    }

    private Cursor getAllContacts() {
        Cursor c = 	db.query(true, CONTACT_TABLE, ALL_CONTACT_KEYS,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    private Cursor getAllKeyFiles() {
        Cursor c = 	db.query(true, KEY_FILE_TABLE, ALL_KEY_FILE_KEYS,
                null, null, null, null, KEY_KEY_FILE_ROW_ID + " DESC", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    
    private Cursor getAllMessages() {
        Cursor c = 	db.query(true, MESSAGE_TABLE, ALL_MESSAGE_KEYS,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    private Cursor getAllMessagesForContactId(long contactId) {
        String where = KEY_CONTACT_ID + "=" + contactId;
        Cursor c = 	db.query(true, MESSAGE_TABLE, ALL_MESSAGE_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    private void deleteAllContacts() {
        Cursor c = getAllContacts();
        if (c.moveToFirst()) {
            do {
                deleteContact(c.getLong(COL_CONTACT_ROW_ID));
            } while (c.moveToNext());
        }
        c.close();
    }

    private void deleteAllKeyFiles() {
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            do {
                deleteKeyFile(c.getLong(COL_KEY_FILE_ROW_ID));
            } while (c.moveToNext());
        }
        c.close();
    }

    private void deleteAllMessages() {
        Cursor c = getAllMessages();
        if (c.moveToFirst()) {
            do {
                deleteMessage(c.getLong(COL_MESSAGE_ROW_ID));
            } while (c.moveToNext());
        }
        c.close();
    }

    private void deleteAllMessagesForContactId(long contactId) {
        Cursor c = getAllMessagesForContactId(contactId);
        if (c.moveToFirst()) {
            do {
                deleteMessage(c.getLong(COL_MESSAGE_ROW_ID));
            } while (c.moveToNext());
        }
        c.close();
    }

    private Cursor getAllMainInboxMessages() {

        // ***** Need to fill in *****

        return null;
    }

    private Contact getContactByEmail(String email) {
        String where = KEY_EMAIL + "=" + email;
        Cursor c = 	db.query(true, CONTACT_TABLE, ALL_CONTACT_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            Contact contact = new Contact(c.getLong(COL_CONTACT_ROW_ID), c.getString(COL_EMAIL), c.getString(COL_NAME), c.getString(COL_IMAGE_URI),
                    c.getInt(COL_CURRENT_SEND_MSG_ID), c.getInt(COL_CURRENT_RECEIVE_MSG_ID));
            c.close();
            return contact;
        }
        return null;
    }

    private String getEmailByContactId(long contactId) {
        String where = KEY_CONTACT_ROW_ID + "=" + contactId;
        Cursor c = 	db.query(true, CONTACT_TABLE, new String[] {KEY_EMAIL},
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            String email = c.getString(COL_EMAIL);
            c.close();
            return email;
        }
        return null;
    }

    private String getNameByContactId(long contactId) {
        String where = KEY_CONTACT_ROW_ID + "=" + contactId;
        Cursor c = 	db.query(true, CONTACT_TABLE, new String[] {KEY_NAME},
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            String name = c.getString(COL_NAME);
            c.close();
            return name;
        }
        return null;
    }

    private Long getContactIdByName(String name) {
        String where = KEY_NAME + "=" + name;
        Cursor c = 	db.query(true, CONTACT_TABLE, new String[] {KEY_CONTACT_ROW_ID},
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            long contactId = c.getLong(COL_CONTACT_ROW_ID);
            c.close();
            return contactId;
        }
        return null;
    }

    private Long getContactIdByEmail(String email) {
        String where = KEY_EMAIL + "=" + email;
        Cursor c = 	db.query(true, CONTACT_TABLE, new String[] {KEY_CONTACT_ROW_ID},
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            long contactId = c.getLong(COL_CONTACT_ROW_ID);
            c.close();
            return contactId;
        }
        return null;
    }

    private boolean savePrimaryKeyFileName (String keyFileName) {
        boolean saved = false;
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            saved = updateKeyFile(new KeyFile(c.getLong(COL_KEY_FILE_ROW_ID), keyFileName, c.getString(COL_PASSCODE)));
            c.close();
        }
        else {
            insertKeyFile(new KeyFile(null, keyFileName, null));
            saved = true;
        }
        return saved;
    }

    private boolean savePrimaryKeyPasscode (String passcode) {
        boolean saved = false;
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            saved = updateKeyFile(new KeyFile(c.getLong(COL_KEY_FILE_ROW_ID), c.getString(COL_FILE_NAME), passcode));
            c.close();
        }
        else {
            insertKeyFile(new KeyFile(null, keyFileName, null));
            saved = true;
        }
        return saved;
    }

    private String getSavedKeyFileName() {
        String keyFileName = null;
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            keyFileName = c.getString(COL_FILE_NAME);
            c.close();
        }
        return keyFileName;
    }

    private String getSavedPasscode() {
        String passcode = null;
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            passcode = c.getString(COL_PASSCODE);
            c.close();
        }
        return passcode;
    }

    private boolean erasePrimaryKeyFileName () {
        boolean erased = false;
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            erased = updateKeyFile(new KeyFile(c.getLong(COL_KEY_FILE_ROW_ID), null, c.getString(COL_PASSCODE)));
            c.close();
        }
        return erased;
    }

    private boolean erasePrimaryKeyPasscode () {
        boolean erased = false;
        Cursor c = getAllKeyFiles();
        if (c.moveToFirst()) {
            erased = updateKeyFile(new KeyFile(c.getLong(COL_KEY_FILE_ROW_ID), c.getString(COL_FILE_NAME), null ));
            c.close();
        }
        return erased;
    }

    private Integer getCurrentSendMsgIdByEmail(String email) {
        String where = KEY_EMAIL + "=" + email;
        Cursor c = 	db.query(true, CONTACT_TABLE, new String[] {KEY_CURRENT_SEND_MSG_ID},
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            int currentSendMsgId = c.getInt(COL_CURRENT_SEND_MSG_ID);
            c.close();
            return currentSendMsgId;
        }
        return null;
    }

    private Integer getCurrentReceiveMsgIdByEmail(String email) {
        String where = KEY_EMAIL + "=" + email;
        Cursor c = 	db.query(true, CONTACT_TABLE, new String[] {KEY_CURRENT_RECEIVE_MSG_ID},
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            int currentReceiveMsgId = c.getInt(COL_CURRENT_RECEIVE_MSG_ID);
            c.close();
            return currentReceiveMsgId;
        }
        return null;
    }

    private boolean setCurrentSendMsgId(String email, int newSendMsgId) {
        boolean updated = false;
        Contact contact = getContactByEmail(email);
        if (contact != null) {
            contact.setCurrentSendMsgId(newSendMsgId);
            updated = updateContact(contact);
        }
        return updated;
    }

    private boolean setCurrentReceiveMsgId(String email, int newReceiveMsgId) {
        boolean updated = false;
        Contact contact = getContactByEmail(email);
        if (contact != null) {
            contact.setCurrentReceiveMsgId(newReceiveMsgId);
            updated = updateContact(contact);
        }
        return updated;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_CONTACT_TABLE_SQL);
            _db.execSQL(CREATE_KEY_FILE_TABLE_SQL);
            _db.execSQL(CREATE_MESSAGE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + KEY_FILE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
            onCreate(_db);
        }
    }
}
