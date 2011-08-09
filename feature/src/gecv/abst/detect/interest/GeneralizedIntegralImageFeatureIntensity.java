/*
 * Copyright 2011 Peter Abeles
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gecv.abst.detect.interest;

import gecv.alg.detect.interest.IntegralImageFeatureIntensity;
import gecv.struct.image.ImageBase;
import gecv.struct.image.ImageFloat32;
import gecv.struct.image.ImageSInt32;


/**
 * @author Peter Abeles
 */
public class GeneralizedIntegralImageFeatureIntensity {

	/**
	 * Computes an approximation to the Hessian's determinant.
	 *
	 * @param integral Integral image transform of input image. Not modified.
	 * @param skip How many pixels should it skip over.
	 * @param size Hessian kernel's size.
	 * @param intensity Output intensity image.
	 */
	public static <T extends ImageBase>
	void hessian( T integral, int skip , int size ,
				  ImageFloat32 intensity) {

		if( integral instanceof ImageFloat32 ) {
			IntegralImageFeatureIntensity.hessian((ImageFloat32)integral,skip,size,intensity);
		} else if( integral instanceof ImageSInt32) {
			IntegralImageFeatureIntensity.hessian((ImageSInt32)integral,skip,size,intensity);
		} else {
			throw new IllegalArgumentException("Unsupported input type");
		}
	}
}