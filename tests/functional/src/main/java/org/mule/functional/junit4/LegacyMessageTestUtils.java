/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.functional.junit4;

import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.core.api.message.ExceptionPayload;
import org.mule.runtime.core.internal.message.InternalMessage;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;

import javax.activation.DataHandler;

/**
 * Provides utility methods to work with the legacy message API for testing purposes only
 *
 * @deprecated tests should not access properties and attachments using the old API.
 */
@Deprecated
public class LegacyMessageTestUtils {

  // TODO(pablo.kraan): API - fix javaodcs as now the methods throw IllegalStateException instead of class cast exception
  private static final String LEGACY_MESSAGE_API_ERROR = "Error trying to access legacy message API";

  private LegacyMessageTestUtils() {}

  /**
   * Gets an outbound property from the message.
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name or key of the property. This must be non-null.
   * @return the property value or null if the property does not exist in the specified scope
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static <T extends Serializable> T getOutboundProperty(Message message, String name) {
    try {
      Method method = message.getClass().getMethod("getOutboundProperty", String.class);
      method.setAccessible(true);
      return (T) method.invoke(message, name);
    } catch (Exception e) {
      throw new IllegalStateException(LEGACY_MESSAGE_API_ERROR, e);
    }
  }

  /**
   * Gets an outbound property from the message and provides a default value if the property is not present on the message in the
   * scope specified. The method will also type check against the default value to ensure that the value is of the correct type.
   * If null is used for the default value no type checking is done.
   *
   * @param <T> the defaultValue type ,this is used to validate the property value type
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name or key of the property. This must be non-null.
   * @param defaultValue the value to return if the property is not in the scope provided. Can be null
   * @return the property value or the defaultValue if the property does not exist in the specified scope
   * @throws IllegalArgumentException if the value for the property key is not assignable from the defaultValue type
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static <T extends Serializable> T getOutboundProperty(Message message, String name, T defaultValue) {
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
    //return ((InternalMessage) message).getOutboundProperty(name, defaultValue);
  }

  /**
   * Gets an outbound property data type from the message.
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name or key of the property. This must be non-null.
   * @return the property data type or null if the property does not exist in the specified scope
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static DataType getOutboundPropertyDataType(Message message, String name) {
    try {
      Method method = message.getClass().getMethod("getOutboundPropertyDataType", String.class);
      method.setAccessible(true);
      return (DataType) method.invoke(message, name);
    } catch (Exception e) {
      throw new IllegalStateException(LEGACY_MESSAGE_API_ERROR, e);
    }
  }

  /**
   * Gets all outbound property names.
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @return all outbound property keys of this message
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static Set<String> getOutboundPropertyNames(Message message) {
    //return ((InternalMessage) message).getOutboundPropertyNames();
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  /**
   * Gets an inbound property from the message.
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name or key of the property. This must be non-null.
   * @return the property value or null if the property does not exist in the specified scope
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static <T extends Serializable> T getInboundProperty(Message message, String name) {
    try {
      Method method = message.getClass().getMethod("getInboundProperty", String.class);
      method.setAccessible(true);
      return (T) method.invoke(message, name);
    } catch (Exception e) {
      throw new IllegalStateException(LEGACY_MESSAGE_API_ERROR, e);
    }
  }

  /**
   * Gets an inbound property from the message and provides a default value if the property is not present on the message in the
   * scope specified. The method will also type check against the default value to ensure that the value is of the correct type.
   * If null is used for the default value no type checking is done.
   *
   * @param <T> the defaultValue type ,this is used to validate the property value type
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name or key of the property. This must be non-null.
   * @param defaultValue the value to return if the property is not in the scope provided. Can be null
   * @return the property value or the defaultValue if the property does not exist in the specified scope
   * @throws IllegalArgumentException if the value for the property key is not assignable from the defaultValue type
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static <T extends Serializable> T getInboundProperty(Message message, String name, T defaultValue) {
    //return ((InternalMessage) message).getInboundProperty(name, defaultValue);
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  /**
   * Gets an inbound property data type from the message.
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name or key of the property. This must be non-null.
   * @return the property data type or null if the property does not exist in the specified scope
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static DataType getInboundPropertyDataType(Message message, String name) {
    //return ((InternalMessage) message).getInboundPropertyDataType(name);
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }


  /**
   * Gets all inbound property names.
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @return all inbound property keys on this message.
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static Set<String> getInboundPropertyNames(Message message) {
    //return ((InternalMessage) message).getInboundPropertyNames();
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  /**
   * If an error occurred during the processing of this message this will return a ErrorPayload that contains the root exception
   * and Mule error code, plus any other related info
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @return The exception payload (if any) attached to this message
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static ExceptionPayload getExceptionPayload(Message message) {
    try {
      Method method = message.getClass().getMethod("getExceptionPayload");
      method.setAccessible(true);
      return (ExceptionPayload) method.invoke(message);
    } catch (Exception e) {
      throw new IllegalStateException(LEGACY_MESSAGE_API_ERROR, e);
    }
  }


  /**
   * Retrieves an attachment with the given name. If the attachment does not exist, null will be returned
   *
   * @param name the name of the attachment to retrieve
   * @return the attachment with the given name or null if the attachment does not exist
   * @throws {@link ClassCastException} if the message is not a internal message.
   * @see DataHandler
   */
  public static DataHandler getInboundAttachment(Message message, String name) {
    //return ((InternalMessage) message).getInboundAttachment(name);
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  /**
   * Retrieves an attachment with the given name. If the attachment does not exist, null will be returned
   *
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @param name the name of the attachment to retrieve
   * @return the attachment with the given name or null if the attachment does not exist
   * @throws {@link ClassCastException} if the message is not a internal message.
   * @see DataHandler
   */
  public static DataHandler getOutboundAttachment(Message message, String name) {
    //return ((InternalMessage) message).getOutboundAttachment(name);
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }


  /**
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @return a set of the names of the attachments on this message. If there are no attachments an empty set will be returned.
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static Set<String> getInboundAttachmentNames(Message message) {
    //return ((InternalMessage) message).getInboundAttachmentNames();
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  /**
   * @param message message used to obtain the data from. Must be a {@link InternalMessage}
   * @return a set of the names of the attachments on this message. If there are no attachments an empty set will be returned.
   * @throws {@link ClassCastException} if the message is not a internal message.
   */
  public static Set<String> getOutboundAttachmentNames(Message message) {
    //return ((InternalMessage) message).getOutboundAttachmentNames();
    // TODO(pablo.kraan): API - implement this method
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

}
