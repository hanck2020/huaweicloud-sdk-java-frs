package com.huaweicloud.frs.client.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaweicloud.frs.access.FrsAccess;
import com.huaweicloud.frs.client.result.LiveDetectResult;
import com.huaweicloud.frs.client.result.LiveDetectSilentResult;
import com.huaweicloud.frs.common.FrsConstant;
import com.huaweicloud.frs.common.FrsException;
import com.huaweicloud.frs.common.ImageType;
import com.huaweicloud.frs.utils.HttpResponseUtils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Live detect service
 * Supports live detect with base64, file and obs url
 */
public class LiveDetectService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private FrsAccess service;
    private String projectId;

    /**
     * Construct live detect service, invoked by frs client
     *
     * @param service   frs access
     * @param projectId project id
     */
    LiveDetectService(FrsAccess service, String projectId) {
        this.service = service;
        this.projectId = projectId;
    }

    private LiveDetectResult liveDetect(String video, ImageType videoType, String actions, String actionTime) throws FrsException, IOException {
        String uri = String.format(FrsConstant.V1.getLiveDetectUri(), this.projectId);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = new HashMap<>();
        if (ImageType.BASE64 == videoType) {
            json.put("video_base64", video);
        } else {
            json.put("video_url", video);
        }
        json.put("actions", actions);
        if (null != actionTime) {
            json.put("action_time", actionTime);
        }

        RequestBody requestBody = RequestBody.create(JSON, mapper.writeValueAsString(json));
        Response httpResponse = this.service.post(uri, requestBody, this.projectId);
        return HttpResponseUtils.httpResponse2Result(httpResponse, LiveDetectResult.class);
    }

    /**
     * Live detect by base64
     *
     * @param videoBase64 Base64 of video
     * @param actions     Actions
     * @param actionTime  Action time
     * @return Result of live detect
     * @throws FrsException Throws while http status code is not 200
     * @throws IOException  IO exception
     */
    public LiveDetectResult liveDetectByBase64(String videoBase64, String actions, String actionTime) throws FrsException, IOException {
        return liveDetect(videoBase64, ImageType.BASE64, actions, actionTime);
    }

    /**
     * Live detect by base64
     *
     * @param videoBase64 Base64 of video
     * @param actions     Actions
     * @return Result of live detect
     * @throws FrsException Throws while http status code is not 200
     * @throws IOException  IO exception
     */
    public LiveDetectResult liveDetectByBase64(String videoBase64, String actions) throws FrsException, IOException {
        return liveDetectByBase64(videoBase64, actions, null);
    }

    /**
     * Live detect by file path
     *
     * @param videoPath  file path of video
     * @param actions    Actions
     * @param actionTime Action time
     * @return Result of live detect
     * @throws FrsException Throws while http status code is not 200
     * @throws IOException  IO exception
     */
    public LiveDetectResult liveDetectByFile(String videoPath, String actions, String actionTime) throws FrsException, IOException {
        File video = new File(videoPath);
        return liveDetectByFile(video, actions, actionTime);
    }
    
    public LiveDetectResult liveDetectByFile(File video, String actions, String actionTime) throws FrsException, IOException {
        String uri = String.format(FrsConstant.V1.getLiveDetectUri(), this.projectId);
        RequestBody videoBody = RequestBody.create(MediaType.parse("application/octet-stream"), video);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("video_file", video.getName(), videoBody)
                .addFormDataPart("actions", actions);
        if (actionTime != null) {
            builder.addFormDataPart("action_time", actionTime);
        }
        RequestBody requestBody = builder.build();
        Response httpResponse = this.service.post(uri, requestBody, this.projectId);
        return HttpResponseUtils.httpResponse2Result(httpResponse, LiveDetectResult.class);
    }

    /**
     * Live detect by file path
     *
     * @param videoPath File path of video
     * @param actions   Actions
     * @return Result of live detect
     * @throws FrsException Throws while http status code is not 200
     * @throws IOException  IO exception
     */
    public LiveDetectResult liveDetectByFile(String videoPath, String actions) throws FrsException, IOException {
        return liveDetectByFile(videoPath, actions, null);
    }

    /**
     * Live detect by obs url
     *
     * @param videoUrl   Obs url of video
     * @param actions    Actions
     * @param actionTime Action time
     * @return Result of live detect
     * @throws FrsException Throws while http status code is not 200
     * @throws IOException  IO exception
     */
    public LiveDetectResult liveDetectByObsUrl(String videoUrl, String actions, String actionTime) throws FrsException, IOException {
        return liveDetect(videoUrl, ImageType.OBSURL, actions, actionTime);
    }

    /**
     * Live detect by obs url
     *
     * @param videoUrl Obs url of video
     * @param actions  Actions
     * @return Result of live detect
     * @throws FrsException Throws while http status code is not 200
     * @throws IOException  IO exception
     */
    public LiveDetectResult liveDetectByObsUrl(String videoUrl, String actions) throws FrsException, IOException {
        return liveDetectByObsUrl(videoUrl, actions, null);
    }
    
    public LiveDetectSilentResult liveDetectSilentByFile(String filePath) throws FrsException, IOException {
    	File imageFile = new File(filePath);
        return liveDetectSilentByFile(imageFile);
    }
    
    /**
     * Silent living detect
     * @author  hanck@szkingdom.com
     * @date 2020-5-28
     * @param photoPath
     * @return Result of silent live detect
     * @throws FrsException
     * @throws IOException
     */
    public LiveDetectSilentResult liveDetectSilentByFile(File photo) throws FrsException, IOException {
        String uri = String.format(FrsConstant.V1.getLiveDetectSilentUri(), this.projectId);
        RequestBody photoBody = RequestBody.create(MediaType.parse("application/octet-stream"), photo);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("image_file", photo.getName(), photoBody);  
        RequestBody requestBody = builder.build();
        Response httpResponse = this.service.post(uri, requestBody, this.projectId);
        return HttpResponseUtils.httpResponse2Result(httpResponse, LiveDetectSilentResult.class);
    }
    
    public LiveDetectSilentResult liveDetectSilentByBase64(String photo) throws FrsException, IOException {
        return liveDetectSilent(photo, ImageType.BASE64);
    }
    
    public LiveDetectSilentResult liveDetectSilentByObsUrl(String photo) throws FrsException, IOException {
        return liveDetectSilent(photo, null);
    }
    
    /**
     * Silent living detect
     * @author  hanck@szkingdom.com
     * @date 2020-5-28
     * @param photo  image_base64 or obs URI
     * @param videoType if image_base64 parameter value is ImageType.BASE64 else null.
     * @return
     * @throws FrsException
     * @throws IOException
     */
    private LiveDetectSilentResult liveDetectSilent(String photo, ImageType videoType) throws FrsException, IOException {
        String uri = String.format(FrsConstant.V1.getLiveDetectSilentUri(), this.projectId);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = new HashMap<>();
        if (ImageType.BASE64 == videoType) {
            json.put("image_base64", photo);
        } else {
            json.put("image_url", photo);//obs URI
        }
        RequestBody requestBody = RequestBody.create(JSON, mapper.writeValueAsString(json));
        Response httpResponse = this.service.post(uri, requestBody, this.projectId);
        return HttpResponseUtils.httpResponse2Result(httpResponse, LiveDetectSilentResult.class);
    }
}
