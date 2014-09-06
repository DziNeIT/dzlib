/*
 * This file is part of sprintj, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Oliver Stanley <http://ollie.pw>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pw.ollie.sprint.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Utility for unzipping a file
 *
 * @author Ian [simplyianm]
 * (modified by Ollie / DziNeIT)
 */
public class Unzipper {
    /**
     * Unzips a file to the given directory. If any exception is thrown, this
     * method will not swallow it and continue.
     *
     * @param file the file to unzip
     * @param target the target directory for the unzipped file
     * @throws IOException if the ZipFile cannot be found
     */
    public static void unzip(File file, File target) throws IOException {
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration files = zipFile.entries();
            while (files.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) files.nextElement();
                try (InputStream eis = zipFile.getInputStream(entry)) {
                    File f = new File(target.getAbsolutePath() + File.separator
                            + entry.getName());

                    if (entry.isDirectory()) {
                        f.mkdirs();
                        continue;
                    } else {
                        f.getParentFile().mkdirs();
                        f.createNewFile();
                    }

                    try (FileOutputStream fos = new FileOutputStream(f)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = eis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }

    private Unzipper() {
        throw new UnsupportedOperationException();
    }
}
