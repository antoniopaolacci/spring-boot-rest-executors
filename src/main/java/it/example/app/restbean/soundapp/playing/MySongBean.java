package it.example.app.restbean.soundapp.playing;

import java.io.Serializable;
import java.util.Objects;

/**
 * MySongBean
 */

public class MySongBean implements Serializable {
  
  private static final long serialVersionUID = 1L;

  private String url;
  private String title;
  private Integer duration;
  private Integer playbackCounter;


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Integer getPlaybackCounter() {
    return playbackCounter;
  }

  public void setPlaybackCounter(Integer playbackCounter) {
    this.playbackCounter = playbackCounter;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MySongBean {\n");
    sb.append("    url: ").append(url).append("\n");
    sb.append("    title: ").append(title).append("\n");
    sb.append("    duration: ").append(duration).append("\n");
    sb.append("    playbackCounter: ").append(playbackCounter).append("\n");
    sb.append("}");
    return sb.toString();
  }

}


