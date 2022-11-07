
package com.tcs.eas.api.tools.common.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * Link
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-05-16T15:58:19.768Z")
public class Link {
  @SerializedName("deprecation")
  private String deprecation = null;

  @SerializedName("href")
  private String href = null;

  @SerializedName("hreflang")
  private String hreflang = null;

  @SerializedName("media")
  private String media = null;

  @SerializedName("rel")
  private String rel = null;

  @SerializedName("templated")
  private Boolean templated = null;

  @SerializedName("title")
  private String title = null;

  @SerializedName("type")
  private String type = null;

  public Link deprecation(String deprecation) {
    this.deprecation = deprecation;
    return this;
  }

   /**
   * Get deprecation
   * @return deprecation
  **/
  @ApiModelProperty(value = "")
  public String getDeprecation() {
    return deprecation;
  }

  public void setDeprecation(String deprecation) {
    this.deprecation = deprecation;
  }

  public Link href(String href) {
    this.href = href;
    return this;
  }

   /**
   * Get href
   * @return href
  **/
  @ApiModelProperty(value = "")
  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Link hreflang(String hreflang) {
    this.hreflang = hreflang;
    return this;
  }

   /**
   * Get hreflang
   * @return hreflang
  **/
  @ApiModelProperty(value = "")
  public String getHreflang() {
    return hreflang;
  }

  public void setHreflang(String hreflang) {
    this.hreflang = hreflang;
  }

  public Link media(String media) {
    this.media = media;
    return this;
  }

   /**
   * Get media
   * @return media
  **/
  @ApiModelProperty(value = "")
  public String getMedia() {
    return media;
  }

  public void setMedia(String media) {
    this.media = media;
  }

  public Link rel(String rel) {
    this.rel = rel;
    return this;
  }

   /**
   * Get rel
   * @return rel
  **/
  @ApiModelProperty(value = "")
  public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
  }

  public Link templated(Boolean templated) {
    this.templated = templated;
    return this;
  }

   /**
   * Get templated
   * @return templated
  **/
  @ApiModelProperty(value = "")
  public Boolean isTemplated() {
    return templated;
  }

  public void setTemplated(Boolean templated) {
    this.templated = templated;
  }

  public Link title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Link type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return Objects.equals(this.deprecation, link.deprecation) &&
        Objects.equals(this.href, link.href) &&
        Objects.equals(this.hreflang, link.hreflang) &&
        Objects.equals(this.media, link.media) &&
        Objects.equals(this.rel, link.rel) &&
        Objects.equals(this.templated, link.templated) &&
        Objects.equals(this.title, link.title) &&
        Objects.equals(this.type, link.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deprecation, href, hreflang, media, rel, templated, title, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Link {\n");
    
    sb.append("    deprecation: ").append(toIndentedString(deprecation)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    hreflang: ").append(toIndentedString(hreflang)).append("\n");
    sb.append("    media: ").append(toIndentedString(media)).append("\n");
    sb.append("    rel: ").append(toIndentedString(rel)).append("\n");
    sb.append("    templated: ").append(toIndentedString(templated)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

