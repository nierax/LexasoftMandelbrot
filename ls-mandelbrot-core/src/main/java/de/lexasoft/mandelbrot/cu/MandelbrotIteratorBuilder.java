/**
 * Copyright (C) 2021 nierax
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.mandelbrot.cu;

import java.util.Optional;
import java.util.OptionalInt;

import de.lexasoft.common.model.Message;
import de.lexasoft.common.model.MessageId;
import de.lexasoft.common.model.MessageList;
import de.lexasoft.common.model.MessageSeverity;
import de.lexasoft.common.model.MessageText;
import de.lexasoft.common.model.Result;
import de.lexasoft.common.model.ResultBuilder;
import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * Builder for Mandelbrot iterator, which calculates the Mandelbrot image.
 * 
 * @see MandelbrotIteratorFast
 * @author nierax
 *
 */
public class MandelbrotIteratorBuilder {

  private Optional<MandelbrotColorize> colorize = Optional.empty();
  private Optional<CalculationArea> calculationArea = Optional.empty();
  private OptionalInt maxIterations = OptionalInt.empty();
  private Optional<ImageArea> imageArea = Optional.empty();
  private Optional<CalculationVersion> calcVersion = Optional.empty();

  private Optional<MandelbrotIterator> iterator = Optional.empty();

  /**
   * Create only with the of() Method.
   */
  private MandelbrotIteratorBuilder() {
  }

  public static MandelbrotIteratorBuilder of() {
    return new MandelbrotIteratorBuilder();
  }

  /**
   * Which colorize object to use.
   * 
   * @param colorize
   * @return MandelbrotIteratorBuilder to use.
   */
  public MandelbrotIteratorBuilder withColorize(MandelbrotColorize colorize) {
    this.colorize = Optional.ofNullable(colorize);
    return this;
  }

  /**
   * Which number of maximum iterations to use.
   * 
   * @param maxIterations
   * @return MandelbrotIteratorBuilder to use.
   */
  public MandelbrotIteratorBuilder withMaxIterations(int maxIterations) {
    this.maxIterations = OptionalInt.of(maxIterations);
    return this;
  }

  /**
   * Which calculation area to use?
   * 
   * @param calculationArea
   * @return MandelbrotIteratorBuilder to use.
   */
  public MandelbrotIteratorBuilder withCalculationArea(CalculationArea calculationArea) {
    this.calculationArea = Optional.ofNullable(calculationArea);
    return this;
  }

  /**
   * Which dimensions should the image have?
   * 
   * @param imageArea
   * @return
   */
  public MandelbrotIteratorBuilder withImageArea(ImageArea imageArea) {
    this.imageArea = Optional.ofNullable(imageArea);
    return this;
  }

  /**
   * Which calculation version should be used.
   * 
   * @param version The calculation version to be used.
   * @return
   */
  public MandelbrotIteratorBuilder withCalculationVersion(CalculationVersion version) {
    this.calcVersion = Optional.ofNullable(version);
    return this;
  }

  /**
   * Allows to set a custom iterator. Can be omitted.
   * 
   * @param iterator the iterator to set
   */
  public MandelbrotIteratorBuilder withIterator(MandelbrotIterator iterator) {
    this.iterator = Optional.ofNullable(iterator);
    return this;
  }

  /**
   * Creates a custom iterator, if there isn't a custom one set.
   * 
   * @return A new Iterator, using the colorize object, if it is given.
   */
  private MandelbrotIterator createIterator() {
    MandelbrotColorize col = colorize.orElse(new MandelbrotBlackWhite());
    CalculationVersion calc = calcVersion.orElse(CalculationVersion.FAST);
    return MandelbrotIterator.of(calc, col);
  }

  /**
   * Check, whether all pre conditions are fulfilled to do the calculation.
   * 
   * @return True, if the calculation may run, false otherwise.
   */
  private Result<Boolean> checkPreConditions() {
    String msg = "Missing parameters %s for calculation.";
    MessageList msgList = MessageList.of();
    if (calculationArea.isEmpty()) {
      msgList.addMessage(//
          Message.of(MessageId.of("param-missing-ca"), //
              MessageText.of(String.format(msg, "calculation area")), //
              MessageSeverity.ERROR));
    }
    if (imageArea.isEmpty()) {
      msgList.addMessage(//
          Message.of(MessageId.of("param-missing-ia"), //
              MessageText.of(String.format(msg, "image area")), //
              MessageSeverity.ERROR));
    }
    if (maxIterations.isEmpty()) {
      msgList.addMessage(//
          Message.of(MessageId.of("param-missing-mi"), //
              MessageText.of(String.format(msg, "maximum iterations")), //
              MessageSeverity.ERROR));
    }

    return ResultBuilder.of(//
        msgList.countMessagesWithSeverity(MessageSeverity.ERROR) == 0)//
        .withMessages(msgList)//
        .build();
  }

  /**
   * Calculate
   * 
   * @return
   */
  public Result<MandelbrotImage> calculate() {
    Result<Boolean> check = checkPreConditions();
    MandelbrotImage image = null;
    Message msg = null;
    if (check.get()) {
      MandelbrotIterator iterator = this.iterator.orElseGet(this::createIterator);
      image = iterator.drawMandelbrot( //
          this.calculationArea.get(), //
          this.maxIterations.getAsInt(), //
          this.imageArea.get());
      if (image == null) {
        msg = Message.of(//
            MessageId.of("unknown-no-result"),
            MessageText.of("Calculation returned without result for unknown reason."), //
            MessageSeverity.ERROR);
      }
    }
    return ResultBuilder.of(image)//
        .withMessages(check.getMessages())//
        .withMessage(msg) //
        .build();
  }
}
