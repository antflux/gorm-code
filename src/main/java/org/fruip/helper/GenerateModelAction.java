/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.fruip.helper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import org.fruip.helper.sql.SqlParser;
import org.fruip.helper.util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class GenerateModelAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        String sqlStr;
        if (selectionModel.hasSelection()) {
            sqlStr = selectionModel.getSelectedText();
        } else {
            sqlStr = document.getText();
        }
        if (sqlStr.equals("")){
            Messages.showErrorDialog("err get sql str","sqlstr err");
            return;
        }
        Path filePath = Paths.get(Objects.requireNonNull(Objects.requireNonNull(FileDocumentManager.getInstance().getFile(editor.getDocument())).getCanonicalPath()));
        SqlParser sqlParser = new SqlParser();
        String result = sqlParser.Execute(sqlStr);
        String canonicalPath = filePath.getParent().toString() + "/" + sqlParser.getFileName();
        if (sqlParser.getErrMsg() != null) {
            Messages.showErrorDialog("create table not found, please check sql syntax", "Generate Model Result");
            return;
        }
        if ("".equals(result) || null == result) {
            Messages.showErrorDialog("create table not found, please check sql syntax", "Generate Model Result");
            return;
        }
        try {
            FileUtil.WriteFile(canonicalPath, result);
            Messages.showInfoMessage("success, file is " + canonicalPath, "Generate Model Result");
            freshProjectFiles(canonicalPath);
        } catch (IOException e) {
            Messages.showInfoMessage("failed, exception info is " + e.toString(), "Generate Model Result");
        }
    }

    // fresh project files to show model file immediately
    private void freshProjectFiles(String path) {
        LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }
}
