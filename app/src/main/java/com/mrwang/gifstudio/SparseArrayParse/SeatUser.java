package com.mrwang.gifstudio.SparseArrayParse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeatUser implements Serializable {

  private static final long serialVersionUID = -597649434900828453L;
  @SerializedName("uid")
  protected int userId;
  @SerializedName("avatar")
  protected String avatarUri;
  @SerializedName("nick")
  protected String nickName;
  @SerializedName("gender")
  protected int gender;

  public int getUserId() {
    return userId;
  }

  public String getAvatarUri() {
    return avatarUri;
  }

  public String getNickName() {
    return nickName;
  }

  public int getGender() {
    return gender;
  }
}
