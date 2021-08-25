package com.example.promena.voter.Api_Modules.Retrofit;


import android.content.Context;

import com.example.promena.voter.Api_Modules.Modules.Login_Module.Change_Password_Module.Change_Password_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Change_Password_Module.Change_Password_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetChatMessage;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetUserMessages;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.SendMsgResponse;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.DashBoard_Module.DashBoard_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Feedback_Module.Fedback_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Feedback_Module.Feedback_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.Add_Member_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.Add_member_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Add_Sub_Member_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Add_Sub_Member_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Sub_Enrollment_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Members_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Notification.Notification_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Forgot_input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Login_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Register_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Login_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Register_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Assembly_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Booth_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Ward_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Synchronize_Module.Synchronize_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Synchronize_Module.Synchronize_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Create_Data_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Create_Data_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Todo_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.User_Module.User_Response;
import com.example.promena.voter.Common.Preferences;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
    /* Context context = null;
    Preferences pref = new Preferences(context);*/

    //Login Api
    @Headers({"Accept: application/json"})
    @POST("api/Authentication/Login")
    Call<Login_Response> login_response(@Body Login_Input login_input);

    //Register Api
    @Headers({"Accept: application/json"})
    @POST("api/AppUsers/RegisterUser")
    Call<Register_Response> register_response (@Body Register_Input register_input);

    //Forgot Password Api
    @Headers({"Accept: application/json"})
    @POST("api/Authentication/ForgotPassword")
    Call<Register_Response> register_response (@Body Forgot_input forgot_input);

    //Logout Api GET Method
    @GET("api/Authentication/LogOut")
    Call<Register_Response> setdata(@Header("AccessToken") String param1);

    //Get Notification Api
    @GET("api/Data/GetNotifications")
    Call<Notification_Response> notification_data(@Header("AccessToken") String param1);

    //Get User Data Api
    @GET("api/AppUsers/GetCurrentUser")
    Call<User_Response> user_response(@Header("AccessToken") String param1);

    //Update Profile Api
    @Headers({"Accept: application/json"})
    @POST("api/AppUsers/UpdateProfile")
    Call<Register_Response> update_response (@Header("AccessToken") String param1,@Body Register_Input register_input);

    //ChangePassword Api
    @Headers({"Accept: application/json"})
    @POST("api/Authentication/ChangePassword")
    Call<Change_Password_Response> change_password_response (@Header("AccessToken") String param1, @Body Change_Password_Input change_password_input);

    //Get Enrollment Api
    @GET("api/Enrollments/GetEnrollmentList")
    Call<Members_Response> members_response (@Header("AccessToken")String param1);

    //Get To do Api
    @GET("api/Tasks/GetTaskList")
    Call<Todo_Response> todo_response (@Header("AccessToken")String param1);

    //Get Completed To do list
    @GET("api/Tasks/GetTaskDoneList")
    Call<Todo_Response> todoo_response (@Header("AccessToken")String param1);

    //Add New Member Api
    @Headers({"Accept: application/json"})
    @POST("api/Enrollments/RegisterEnrollment")
    Call<Add_member_Response> add_member_response(@Header("AccessToken") String param1, @Body Add_Member_Input add_member_input);

    //Add Task Api
    @Headers({"Accept: application/json"})
    @POST("api/Tasks/CreateTask")
    Call<Create_Data_Response> create_data_response(@Header("AccessToken") String param1, @Body Create_Data_Input create_data_input);

    //Update Task Api
    @Headers({"Accept: application/json"})
    @POST("api/Tasks/UpdateTask")
    Call<Create_Data_Response> create_data_responsee(@Header("AccessToken") String param1, @Body Create_Data_Input create_data_input);

    //Add Sub Enrollment Api
    @Headers({"Accept: application/json"})
    @POST("api/SubEnrollments/PostSubEnrollment")
    Call<Add_Sub_Member_Response> add_sub_response(@Header("AccessToken") String param1, @Body Add_Sub_Member_Input add_sub_member_input);

    //Add Feedback Api
    @Headers({"Accept: application/json"})
    @POST("api/Data/PostFeedback")
    Call<Feedback_Response> feedback_response (@Header("AccessToken") String param1, @Body Fedback_Input fedback_input);

    //Sync Datas Api
    @Headers({"Accept: application/json"})
    @POST("api/Data/Synchronize")
    Call<Synchronize_Response> synchronize_response (@Header("AccessToken") String param1, @Body Synchronize_Input synchronize_input);

    //Get SubEnrollment Data
    @GET
    Call<Sub_Enrollment_Response> sub_enrollment_response(@Url String url, @Header("AccessToken") String param1);

    @GET("api/Data/GetAssemblyList")
    Call<Assembly_Response> assembly_response();

    //Get ward Data
    @GET
    Call<Ward_Response> ward_response(@Url String url);

    //Update Members profile
    @Headers({"Accept:application/json"})
    @POST("api/Enrollments/UpdateEnrollment")
    Call<Add_member_Response> update_member_response(@Header("AccessToken") String param1, @Body Add_Member_Input update_member_input);

    //DashBoard Api
    @GET("api/Data/GetDashboardData")
    Call<DashBoard_Response> dashboard_response(@Header("AccessToken") String param1);

    //Update Sub Enrollment Api
    @Headers("Accept: application/json")
    @POST("api/SubEnrollments/PutSubEnrollment")
    Call<Add_Sub_Member_Response> updtae_sub_response(@Header("AccessToken") String param1, @Body Add_Sub_Member_Input update_sub_input );

    //Booth Api
    @GET
    Call<Booth_Response> booth_response (@Url String url);

    //Add Client VoterId search
    @Headers({"Accept: application/json"})
    //@FormUrlEncoded
    @POST("api/ManageVoterList/GetVotersDetails")
    Call<VoterId_Search_Response> getVotersDetails (@Header("AccessToken") String param1,@Query("voterId") String voterId);

    @GET("api/Chat/GetUsersMessage")
    Call<GetUserMessages> getUsersMessage(@Header("AccessToken") String param1);

    @GET("api/Chat/GetMessage")
    Call<GetChatMessage> getMessage(@Header("AccessToken") String param1, @Query("toUserId") Integer userID, @Query("pageIndex") Integer pageIndex, @Query("pageSize") Integer pageSize);

    @Headers({"Accept: application/json"})
    @POST("api/Chat/SendMessage")
    Call<SendMsgResponse> sendMessage(@Header("AccessToken") String param1, @Query("toUserId") Integer userId, @Query("message") String message);
}



