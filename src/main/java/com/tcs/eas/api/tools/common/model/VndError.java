
package com.tcs.eas.api.tools.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Error response consisting of an error reference and a brief description of the error.
 */
@ApiModel(description = "Error response consisting of an error reference and a brief description of the error.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-16T15:58:19.768Z")
public class VndError {
  @SerializedName("_links")
  private List<Link> links = null;

  @SerializedName("logref")
  private String logref = null;

  @SerializedName("message")
  private String message = null;

  public VndError links(List<Link> links) {
    this.links = links;
    return this;
  }

  public VndError addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<Link>();
    }
    this.links.add(linksItem);
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public VndError logref(String logref) {
    this.logref = logref;
    return this;
  }

   /**
   * A unique reference for the error instance, for audit purposes.
   * @return logref
  **/
  @ApiModelProperty(value = "A unique reference for the error instance, for audit purposes.")
  public String getLogref() {
    return logref;
  }

  public void setLogref(String logref) {
    this.logref = logref;
  }

  public VndError message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VndError vndError = (VndError) o;
    return Objects.equals(this.links, vndError.links) &&
        Objects.equals(this.logref, vndError.logref) &&
        Objects.equals(this.message, vndError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, logref, message);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VndError {\n");
    
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    logref: ").append(toIndentedString(logref)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

