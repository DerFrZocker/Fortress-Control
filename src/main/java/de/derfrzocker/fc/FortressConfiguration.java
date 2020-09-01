/*
 * MIT License
 *
 * Copyright (c) 2020 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.derfrzocker.fc;

// Note Minecraft Recommends following Ranges
// spacing: 0 - 4096
// separation: 0 - 4096
// salt: 2147483647

// Default values
// spacing: 27
// separation: 4
// salt: 30084232
public class FortressConfiguration {

    public static final int DEFAULT_SPACING = 27;
    public static final int DEFAULT_SEPARATION = 4;
    public static final int DEFAULT_SALT = 30084232;

    private final int spacing;
    private final int separation;
    private final int salt;

    public FortressConfiguration(int spacing, int separation, int salt) {
        this.spacing = spacing;
        this.separation = separation;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "FortressConfiguration{" +
                "spacing=" + spacing +
                ", separation=" + separation +
                ", salt=" + salt +
                '}';
    }

    public int getSpacing() {
        return spacing;
    }

    public int getSeparation() {
        return separation;
    }

    public int getSalt() {
        return salt;
    }

}
