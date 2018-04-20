/******************************************************************************
 * Copyright © 2013-2016 The Nxt Core Developers.                             *
 *                                                                            *
 * See the AUTHORS.txt, DEVELOPER-AGREEMENT.txt and LICENSE.txt files at      *
 * the top-level directory of this distribution for the individual copyright  *
 * holder information and the developer policies on copyright and licensing.  *
 *                                                                            *
 * Unless otherwise agreed in a custom licensing agreement, no part of the    *
 * Nxt software, including this file, may be copied, modified, propagated,    *
 * or distributed except according to the terms contained in the LICENSE.txt  *
 * file.                                                                      *
 *                                                                            *
 * Removal or modification of this copyright notice is prohibited.            *
 *                                                                            *
 ******************************************************************************/

package xin.env;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

abstract class UserDirProvider implements DirProvider {

    public static final String LOG_FILE_PATTERN = "log4j.appender.file.File";

    private File logFileDir;

    @Override
    public boolean isLoadPropertyFileFromUserDir() {
        return true;
    }

    @Override
    public void updateLogFileHandler(Properties loggingProperties) {

        if (loggingProperties.getProperty(LOG_FILE_PATTERN) == null) {
            logFileDir = new File(getUserHomeDir(), "logs");
            return;
        }

        Path logFilePattern = Paths.get(getUserHomeDir()).resolve(Paths.get(loggingProperties.getProperty(LOG_FILE_PATTERN)));
        loggingProperties.setProperty(LOG_FILE_PATTERN, logFilePattern.toString());

        Path logDirPath = logFilePattern.getParent();
        System.out.printf("Logs dir %s\n", logDirPath.toString());

        this.logFileDir = new File(logDirPath.toString());

        if (!Files.isReadable(logDirPath)) {
            System.out.printf("Creating dir %s\n", logDirPath);
            try {
                Files.createDirectory(logDirPath);
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot create " + logDirPath, e);
            }
        }
    }

    @Override
    public File getLogFileDir() {
        return logFileDir;
    }

    @Override
    public String getDbDir(String dbDir) {
        return Paths.get(getUserHomeDir()).resolve(Paths.get(dbDir)).toString();
    }

    @Override
    public File getConfDir() {
        return new File(getUserHomeDir(), "conf");
    }

    @Override
    public abstract String getUserHomeDir();

}
