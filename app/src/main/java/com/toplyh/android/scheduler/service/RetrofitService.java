package com.toplyh.android.scheduler.service;

import com.toplyh.android.scheduler.service.entity.remote.AddSprint;
import com.toplyh.android.scheduler.service.entity.remote.Count;
import com.toplyh.android.scheduler.service.entity.remote.HttpsResult;
import com.toplyh.android.scheduler.service.entity.remote.Meeting;
import com.toplyh.android.scheduler.service.entity.remote.Member;
import com.toplyh.android.scheduler.service.entity.remote.MetAndMem;
import com.toplyh.android.scheduler.service.entity.remote.MsgCount;
import com.toplyh.android.scheduler.service.entity.remote.PJS;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.entity.remote.Projects;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;
import com.toplyh.android.scheduler.service.entity.remote.UpdateCount;
import com.toplyh.android.scheduler.service.entity.state.Token;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 我 on 2017/11/15.
 */

public interface RetrofitService {

    @POST("user")
    Observable<HttpsResult<String>> registerUser(@Body Count count);

    @POST("user/update")
    Observable<HttpsResult<String>> updateUser(@Body UpdateCount updateCount);

    @GET("user/msg")
    Observable<HttpsResult<MsgCount>> getUserMsg();

    @POST("authentication")
    Observable<HttpsResult<Token>> loginUser(@Body Count count);

    @GET("project/all")
    Observable<HttpsResult<Projects>> getProjects();

    @POST("project/addProjectAndMember")
    Observable<HttpsResult<String>> newProject(@Body ProjectAndMember projectAndMember);

    @GET("sprint/show-by-project-and-status")
    Observable<HttpsResult<List<Sprint>>> getSprintByProjectAndStatus(@Query(value = "projectId") Integer projectId,@Query(value = "status")String status);

    @POST("sprint/add")
    Observable<HttpsResult<String>> newSprint(@Body AddSprint addSprint);

    @GET("sprint/change/status")
    Observable<HttpsResult<String>> changeSprintStatus(@Query(value = "sprintId") Integer sprintId,@Query(value = "status") String status);

    @GET("meeting/show")
    Observable<HttpsResult<List<MetAndMem>>> getMeetings(@Query(value = "projectId") Integer projectId);

    @POST("meeting/addMeetingAndMember")
    Observable<HttpsResult<String>> newMeeting(@Body MetAndMem metAndMem);

    @GET("member/show")
    Observable<HttpsResult<List<Member>>> getMembers(@Query(value = "projectId") Integer projectId);

    @GET("project/progress")
    Observable<HttpsResult<Integer>> getProgress(@Query(value = "projectId") Integer projectId);

    @POST("hello-convert-and-send")
    Observable<Void> sendRestEcho(@Query("msg") String message);

    @Multipart
    @POST("")
    public String uploadFile(@Part("file") RequestBody body, @PartMap Map<String,Object> map);

    @Multipart
    @POST("upload")
    Observable<String> upload(@Part MultipartBody.Part file);
}
