package com.fruitvendorapp.server_networking;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.SessionManager;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestHelper {
    private static final String TAG = RequestHelper.class.getSimpleName();

    public static void PostRequest(final int requestCode, final Context context, String url, JSONObject jsonObject, final ResponseListener responseListener) {
        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorMessage : " + anError.getMessage());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(context, msg);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (anError.getErrorCode() == 422) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 400) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 401) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 402) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }

    public static void PostTwilioRequest(final int requestCode, final Context context, String authKey, String url, JSONObject jsonObject, final ResponseListener responseListener) {
        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("X-Authy-API-Key", authKey)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorMessage : " + anError.getMessage());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(context, msg);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (anError.getErrorCode() == 422) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 400) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 401) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 402) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }

    public static void GetTwilioRequest(final int requestCode, final Context context, String authKey, String url, final ResponseListener responseListener) {
        AndroidNetworking.get(url)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("X-Authy-API-Key", authKey)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorMessage : " + anError.getMessage());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(context, msg);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (anError.getErrorCode() == 422) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 400) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 401) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 402) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }

    public static void getRequest(final int requestCode, final Context context, String url, final JsonArrayResponseListener jsonArrayResponseListener) {
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response = ", response + "");
                        jsonArrayResponseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        jsonArrayResponseListener.onError(anError);
                    }
                });

    }

    public static void PostTokenRequest(final int requestCode, final Context context, String url, JSONObject jsonObject, final ResponseListener responseListener) {

        Log.d("LINK", "PostTokenRequest: "+ url);

        Map<String,Integer> params = new HashMap<String, Integer>();
        params.put("delivery_type",5);

        AndroidNetworking.post(url)
               // .addBodyParameter(params)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Token " + new SessionManager(context).getToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorMessage : " + anError.getMessage());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(context, msg);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (anError.getErrorCode() == 422) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 400) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");

                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 401) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 402) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }

    public static void PostJSonTokenRequest(final int requestCode, final Context context, String url, JSONArray jsonObject, final ResponseListener responseListener) {
        AndroidNetworking.post(url)
                .addJSONArrayBody(jsonObject)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Token " + new SessionManager(context).getToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorMessage : " + anError.getMessage());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(context, msg);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (anError.getErrorCode() == 422) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 400) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");

                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 401) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 402) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }


    public static void getRequestWithToken(final int requestCode, final Context context, String url, final ResponseListener responseListener) {

        Log.d(TAG, "getRequestWithToken: " + url  + "Token " + new SessionManager(context).getToken());
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Token " + new SessionManager(context).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response = ", response + "");
                        responseListener.onSuccess(requestCode, response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        responseListener.onError(anError);
                    }
                });

    }


    public static void getRequestWithJSonArrayToken(final int requestCode, final Context context, String url, final JsonArrayResponseListener jsonArrayResponseListener) {
        Log.d(TAG, "getRequestWithJSonArrayToken: " + url + " " + new SessionManager(context).getToken() );
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Token " + new SessionManager(context).getToken())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response = ", response + "");
                        jsonArrayResponseListener.onSuccess(requestCode, response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        jsonArrayResponseListener.onError(anError);
                    }
                });

    }

    public static void putRequestWithToken(Context mContext, final int requestCode, String url, JSONObject jsonObject, ResponseListener responseListener) {
        Log.e(TAG, new SessionManager(mContext).getToken());
        AndroidNetworking.put(url)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Token " + new SessionManager(mContext).getToken()).addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }

    public static void deleteRequestWithToken(Context context, final int requestCode, String url, final ResponseListener responseListener) {
        AndroidNetworking.delete(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Token " + new SessionManager(context).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response = ", response + "");
                        responseListener.onSuccess(requestCode, response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        checkErrorCode(context, anError);
                        responseListener.onError(anError);
                    }
                });

    }

    public static void checkErrorCode(Context context, ANError anError) {
        if (anError.getErrorCode() != 0) {
            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
            if (anError.getErrorCode() == 404) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    String msg = object.optString("message");
                    BaseUtility.toastMsg(context, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (anError.getErrorCode() == 402) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    String msg = object.optString("message");
                    BaseUtility.toastMsg(context, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
        }
    }


    public static void patchRequest(final int requestCode, final Context mContext, String url, JSONObject jsonObject, final ResponseListener responseListener) {
        AndroidNetworking.patch(url)
                .addHeaders("Authorization", "Token " + new SessionManager(mContext).getToken())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response = ", response + "");
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(mContext, msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError(anError);
                    }
                });
    }


    public static void patchRequestWithToken(Context mContext, final int requestCode, String url, JSONObject jsonObject, ResponseListener responseListener) {
        Log.e(TAG, new SessionManager(mContext).getToken());
        AndroidNetworking.patch(url)
                .addHeaders("Authorization", "Token " + new SessionManager(mContext).getToken())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkErrorCode(mContext, anError);
                        responseListener.onError(anError);
                    }
                });
    }



  /*  public static void verifyHeaderRequest(Context mContext, final int requestCode, String url, ResponseListener responseListener) {
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("authorization", new SessionManager(mContext).getAccessToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError();
                    }
                });
    }



    public static void PostTokenRequest(final int requestCode, final Context context, String url, JSONObject jsonObject, final ResponseListener responseListener) {
        Log.e("auth token = ", new SessionManager(context).getAccessToken());
        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .addHeaders("authorization", new SessionManager(context).getAccessToken())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            if (anError.getErrorCode() == 404) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String msg = object.optString("message");
                                    BaseUtility.toastMsg(context, msg);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (anError.getErrorCode() == 422) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String error = object.optString("message");
                                    // BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 401) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    // BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else if (anError.getErrorCode() == 402) {
                                String json = anError.getErrorBody();
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String status = object.optString("success");
                                    String error = object.optString("message");
                                    BaseUtility.toastMsg(context, error);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError();
                    }
                });
    }

    public static void deleteRequestWithToken(Context context, final int requestCode, String url, final ResponseListener responseListener) {
        AndroidNetworking.delete(url)
                .setPriority(Priority.HIGH)
                .addHeaders("authorization", new SessionManager(context).getAccessToken())
                .addHeaders("Accept", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response = ", response + "");
                        responseListener.onSuccess(requestCode, response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        checkErrorCode(context, anError);
                        responseListener.onError();
                    }
                });

    }

    public static void putRequestWithToken(Context mContext, final int requestCode, String url, JSONObject jsonObject, ResponseListener responseListener) {
        Log.e(TAG, new SessionManager(mContext).getAccessToken());
        AndroidNetworking.put(url)
                .addHeaders("authorization", new SessionManager(mContext).getAccessToken())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        checkErrorCode(mContext, anError);
                        responseListener.onError();
                    }
                });
    }

    public static void verifySubUserHeaderRequest(Context mContext, final int requestCode, String url, JSONObject jsonObject, ResponseListener responseListener) {
        AndroidNetworking.post(url)
                .addHeaders("authorization", new SessionManager(mContext).getAccessToken())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onSuccess(requestCode, response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                        responseListener.onError();
                    }
                });
    }


    public static void checkErrorCode(Context context, ANError anError) {
        if (anError.getErrorCode() != 0) {
            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
            if (anError.getErrorCode() == 404) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    String msg = object.optString("message");
                    BaseUtility.toastMsg(context, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (anError.getErrorCode() == 402) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    String msg = object.optString("message");
                    BaseUtility.toastMsg(context, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
        }
    }
*/
}