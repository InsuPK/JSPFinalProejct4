package com.example.jspfinalproejct4.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.jspfinalproejct4.bean.BoardVO;
import com.example.jspfinalproejct4.dao.BoardDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUpload {
    public BoardVO uploadPhoto(HttpServletRequest request){
        String filename = "";
        int sizeLimit = 15 * 1024 * 1024;

        String realPath = request.getServletContext().getRealPath("upload");

        File dir = new File(realPath);
        if (!dir.exists()) dir.mkdirs();

        BoardVO one = null;
        MultipartRequest multipartRequest = null;
        try {
            multipartRequest = new MultipartRequest(request, realPath, sizeLimit, "utf-8",
                    new DefaultFileRenamePolicy());
            filename = multipartRequest.getFilesystemName("photo");
            one = new BoardVO();
            String sid = multipartRequest.getParameter("seq");
            if (sid!=null && !sid.equals("")) one.setSeq(Integer.parseInt(sid));
            one.setCategory(multipartRequest.getParameter("category"));
            one.setContent(multipartRequest.getParameter("content"));
            one.setWriter(multipartRequest.getParameter("writer"));
            one.setTitle(multipartRequest.getParameter("title"));

            if (sid!=null && !sid.equals("")) {
                BoardDAO dao = new BoardDAO();
                String oldfilename = dao.getPhotoFilename(Integer.parseInt(sid));
                if (filename!=null && oldfilename!=null)
                    FileUpload.deleteFile(request, oldfilename);
                else if (filename==null && oldfilename!=null)
                    filename = oldfilename;
            }
            one.setPhoto(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return one;
    }

    public static void deleteFile(HttpServletRequest request, String filename) {
        String filePath = request.getServletContext().getRealPath("upload");

        File f = new File(filePath + "/" + filename);
        if (f.exists()) f.delete();
    }





}
