"use strict";

process.env.SESSION_COOKIE_MAXAGE = 7 * 24 * 60 * 60 * 1000; // 1 week
process.env.SESSION_COOKIE_SECURE = false;
process.env.SESSION_NAME = "SSID";
process.env.SESSION_SECRET = "";
process.env.SESSION_TABLE_NAME = "ssids";

process.env.SALT_LENGTH = 5;
process.env.HASH_ALGORITHM = "sha512";
