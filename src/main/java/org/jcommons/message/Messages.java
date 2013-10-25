package org.jcommons.message;

import static org.jcommons.message.Flatten.flatten;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

/**
 * A composite message that collects many message in one go and acts as the default message container.
 * 
 * @author Thorsten Goeckeler
 */
public class Messages
  implements Message
{
  private final List<Message> messages = new ArrayList<Message>();

  /** {@inheritDoc} */
  @Override
  public final Message add(final Message text) {
    if (text != null) messages.add(text);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Message clear() {
    messages.clear();
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final String getFaults() {
    // filter on errors only
    List<Message> errors = new LinkedList<>();
    for (Message message : flatten(this)) {
      if (!message.isComposite() && message.isError()) {
        errors.add(message);
      }
    }
    // map errors to their strings
    return StringUtils.join(errors, " ");
  }

  /** {@inheritDoc} */
  @Override
  public final String getInfos() {
    // filter on infos only
    List<Message> infos = new LinkedList<>();
    for (Message message : flatten(this)) {
      if (!message.isComposite() && message.isInfo()) {
        infos.add(message);
      }
    }
    // map infos to their strings
    return StringUtils.join(infos, " ");
  }

  /** {@inheritDoc} */
  @Override
  public final String getText() {
    // map all messages to their strings
    return StringUtils.join(messages, " ");
  }

  /** {@inheritDoc} */
  @Override
  public final List<? extends Message> getTexts() {
    return flatten(this);
  }

  /** {@inheritDoc} */
  @Override
  public final List<? extends Message> getMessages() {
    return Collections.unmodifiableList(messages);
  }

  /** {@inheritDoc} */
  @Override
  public final String getWarnings() {
    // filter on warnings only
    List<Message> warnings = new LinkedList<>();
    for (Message message : flatten(this)) {
      if (!message.isComposite() && message.isWarning()) {
        warnings.add(message);
      }
    }
    // map warnings to their strings
    return StringUtils.join(warnings, " ");
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isEmpty() {
    return messages.isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isComposite() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isError() {
    for (Message message : flatten(this)) {
      if (message.isError()) return true;
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isInfo() {
    for (Message message : flatten(this)) {
      if (message.isInfo()) return true;
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isWarning() {
    for (Message message : flatten(this)) {
      if (message.isWarning()) return true;
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final Message remove(final Message text) {
    if (text != null) messages.remove(text);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getText();
  }
}

/**
 * Flatten a message tree so that only concrete messages remain on the top level list.
 * 
 * @author Thorsten Goeckeler
 */
final class Flatten
{
  /** hide sole constructor */
  private Flatten() {
  }

  /**
   * Flat out the message tree.
   * 
   * @param message the current message to flat out
   * @return a list containing only concrete messages
   */
  public static List<Message> flatten(final Message message) {
    if (!message.isComposite()) return Collections.singletonList(message);

    List<? extends Message> all = message.getMessages();
    List<Message> messages = new ArrayList<Message>(Math.max(10, all.size()));

    for (Message current : all) {
      messages.addAll(flatten(current));
    }

    return messages;
  }
}
