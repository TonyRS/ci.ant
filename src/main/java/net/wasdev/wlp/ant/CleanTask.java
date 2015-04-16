/**
 * (C) Copyright IBM Corporation 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.wasdev.wlp.ant;

import java.io.File;
import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;

/**
 * Clean the logs directory of a Liberty profile server instance
 */
public class CleanTask extends AbstractTask {

    /*
     * (non-Javadoc)
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() {
        super.initTask();
        try {
            if (isServerStopped() == true) {

                File serverOutputDir = null;
                // Verify if the outputDir null or not null and if the
                // serverName is null or not null
                if ((this.outputDir != null || this.outputDir == null)
                        && (this.serverName == "defaultServer" || serverName != "defaultServer")) {
                    serverOutputDir = new File(this.serverOutputDir, "/logs");
                    // Remove all content from a specific directory.
                    cleanDirectory(serverOutputDir);
                    log(MessageFormat.format(
                            messages.getString("info.clean.successful"),
                            "serverOutputDir", serverOutputDir));
                }
            } else {
                throw new BuildException(
                        messages.getString("error.clean.fail"), getLocation());
            }
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    /**
     * Remove all content a specific directory
     *
     * @param dir
     *            A directory used as a parameter in this method
     */
    protected void cleanDirectory(File dir) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    cleanDirectory(file);
                }
                file.delete();
            }
        }
    }
}