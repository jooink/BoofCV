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

package gecv.abst.detect.corner;

import gecv.abst.detect.extract.CornerExtractor;
import gecv.alg.detect.extract.SelectNBestCorners;
import gecv.struct.QueueCorner;
import gecv.struct.image.ImageBase;

/**
 * Base class for extracting corners. Can return all the found corners or just the corners with the highest
 * intensity.
 *
 * @author Peter Abeles
 */
public class CornerDetectorBase<I extends ImageBase> {

	// selects the features with the largest intensity
	protected SelectNBestCorners selectBest;

	// extracts corners from the intensity image
	protected CornerExtractor extractor;
	// list of corners found by the extractor
	protected QueueCorner foundCorners;

	// optional: list of candidate corners
	protected QueueCorner candidateCorners;
	// optional: number of corners it should try to find
	protected int requestedFeatureNumber;

	/**
	 * @param extractor   Extracts the corners from intensity image
	 * @param maxFeatures If not zero then only the best features are returned up to this number
	 */
	public CornerDetectorBase(CornerExtractor extractor, int maxFeatures) {
		this.extractor = extractor;
		this.foundCorners = new QueueCorner(maxFeatures);
		if (maxFeatures > 0) {
			selectBest = new SelectNBestCorners(maxFeatures);
		}
	}

	/**
	 * <p>
	 * If a candidate list of corners is provided then only corners at the specified location are considered.
	 * </p>
	 * <p/>
	 * <p>
	 * If the provided corner extractor does not support this feature then an exception is thrown.
	 * </p>
	 *
	 * @param candidateCorners A list of pixels were corners might exist.
	 */
	public void setCandidateCorners(QueueCorner candidateCorners) {
		if (!extractor.getUsesCandidates())
			throw new IllegalArgumentException("The provided corner extractor use candidate corners");

		this.candidateCorners = candidateCorners;
	}

	/**
	 * <p>
	 * Specifies how many corners should be returned.
	 * </p>
	 * <p/>
	 * <p>
	 * If the provided corner extractor does not support this feature then an exception is thrown.
	 * </p>
	 *
	 * @param requestedFeatureNumber The number of corners it should return.
	 */
	public void setRequestedFeatureNumber(int requestedFeatureNumber) {
		if (!extractor.getAcceptRequest())
			throw new IllegalArgumentException("The provided corner extractor does not accept requests for the number of detected features.");

		this.requestedFeatureNumber = requestedFeatureNumber;
	}

	public QueueCorner getCorners() {
		if (selectBest != null) {
			return selectBest.getBestCorners();
		} else
			return foundCorners;
	}
}
