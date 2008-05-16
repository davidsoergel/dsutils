/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.davidsoergel.dsutils.collections;

import com.google.common.collect.TreeMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * A data structure that maps pairs of keys to values, and is queryable in both directions (i.e., also in the value->keys direction).  The order of the keys is unimportant.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class Symmetric2dBiHashMap<K, V>
	{

	private class SymmetricPairwiseDistanceMatrix
		{
		// really we wanted a SortedBiMultimap or something, but lacking that, we just store the inverse map explicitly.

		private TreeMultimap<Double, KeyPair<T>> distanceToPair = new TreeMultimap<Double, KeyPair<T>>();
		private Map<KeyPair<T>, Double> pairToDistance = new HashMap<KeyPair<T>, Double>();

		//private Set<LengthWeightHierarchyNode<T>> theActiveNodes = new HashSet<LengthWeightHierarchyNode<T>>();
		private Multimap<LengthWeightHierarchyNode<Cluster<T>>, KeyPair<T>> nodeToPairs = Multimaps.newHashMultimap();


		/*SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double> theDistanceMatrix =
				new SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double>();*/


		void addInitialPair(LengthWeightHierarchyNode<Cluster<T>> node1, LengthWeightHierarchyNode<Cluster<T>> node2)
			{
			Double d = distanceMeasure
					.distanceFromTo(node1.getValue().getCentroid(), node2.getValue().getCentroid());
			KeyPair<T> pair = getOrCreateNodePair(node1, node2);

			distanceToPair.put(d, pair);
			pairToDistance.put(pair, d);
			}

		void addAndComputeDistances(LengthWeightHierarchyNode<Cluster<T>> node)
			{
			Set<LengthWeightHierarchyNode<Cluster<T>>> activeNodes =
					new HashSet(nodeToPairs.keySet());// avoid ConcurrentModificationException

			/*	Double d = distanceMeasure.distanceFromTo(node.getValue().getCentroid(),
														 node.getValue().getCentroid());// probably 0, but you never know
			   NodePair<T> pair = getOrCreateNodePair(node, node);

			   distanceToPair.put(d, pair);
			   pairToDistance.put(pair, d);
   */
			for (LengthWeightHierarchyNode<Cluster<T>> theActiveNode : activeNodes)
				{
				Double d = distanceMeasure
						.distanceFromTo(node.getValue().getCentroid(), theActiveNode.getValue().getCentroid());
				KeyPair<T> pair = getOrCreateNodePair(node, theActiveNode);

				distanceToPair.put(d, pair);
				pairToDistance.put(pair, d);
				}
			}

		void setDistance(LengthWeightHierarchyNode<Cluster<T>> node1, LengthWeightHierarchyNode<Cluster<T>> node2,
		                 double d)
			{
			setDistance(getOrCreateNodePair(node1, node2), d);
			}

		private KeyPair getOrCreateNodePair(LengthWeightHierarchyNode<Cluster<T>> node1,
		                                     LengthWeightHierarchyNode<Cluster<T>> node2)
			{
			KeyPair<T> pair = getNodePair(node1, node2);
			if (pair == null)
				{
				pair = new KeyPair(node1, node2);
				nodeToPairs.put(node1, pair);
				nodeToPairs.put(node2, pair);
				}
			return pair;
			}

		private KeyPair<T> getNodePair(LengthWeightHierarchyNode<Cluster<T>> node1,
		                                LengthWeightHierarchyNode<Cluster<T>> node2)
			{
			try
				{
				return CollectionUtils.intersection(nodeToPairs.get(node1), nodeToPairs.get(node2)).iterator().next();
				}
			catch (NoSuchElementException e)
				{
				return null;
				}
			}

		private void setDistance(KeyPair keyPair, double d)
			{
			Double oldDistance = pairToDistance.get(keyPair);
			if (oldDistance != null)
				{
				pairToDistance.remove(keyPair);
				distanceToPair.remove(oldDistance, keyPair);
				}
			pairToDistance.put(keyPair, d);
			distanceToPair.put(d, keyPair);
			}

		public KeyPair<T> getClosestPair()
			{
			Double closestDistance = distanceToPair.keySet().first();// distanceToPair is sorted
			return distanceToPair.get(closestDistance).first();
			}

		public Double getDistance(LengthWeightHierarchyNode<Cluster<T>> node1,
		                          LengthWeightHierarchyNode<Cluster<T>> node2)
			{
			return getDistance(getNodePair(node1, node2));
			}

		private Double getDistance(KeyPair keyPair)
			{
			return pairToDistance.get(keyPair);
			}

		public void remove(LengthWeightHierarchyNode<Cluster<T>> b)
			{
			for (KeyPair<T> pair : nodeToPairs.get(b))
				{
				Double oldDistance = pairToDistance.remove(pair);
				try
					{
					distanceToPair.remove(oldDistance, pair);
					asdfasdf
					}
				catch (NullPointerException e)
					{
					// no problem
					}
				}
			nodeToPairs.removeAll(b);
			}

		public Set<LengthWeightHierarchyNode<Cluster<T>>> getActiveNodes()
			{
			return nodeToPairs.keySet();
			}

		public int size()
			{
			return nodeToPairs.keySet().size();
			}
		}

	/**
	 * Represent a pair of keys, guaranteeing that node1 <= node2 for the sake of symmetry
	 */
	private class KeyPair<K> implements Comparable
		{
		private K key1;
		private K node2;

		private KeyPair(K key1, K node2)
			{
			if (key1.hashCode() <= node2.hashCode())
				//if (node1.getValue().compareTo(node2.getValue()) <= 0)
				{
				this.key1 = key1;
				this.node2 = node2;
				}
			else
				{
				this.key1 = node2;
				this.node2 = key1;
				}
			}

		public boolean equals(Object o)
			{
			if (this == o)
				{
				return true;
				}
			if (!(o instanceof KeyPair))
				{
				return false;
				}

			KeyPair keyPair = (KeyPair) o;

			if (key1 != null ? !key1.equals(keyPair.key1) : keyPair.key1 != null)
				{
				return false;
				}
			if (node2 != null ? !node2.equals(keyPair.node2) : keyPair.node2 != null)
				{
				return false;
				}

			return true;
			}

		public int hashCode()
			{
			int result;
			result = (key1 != null ? key1.hashCode() : 0);
			result = 31 * result + (node2 != null ? node2.hashCode() : 0);
			return result;
			}

		public K getKey1()
			{
			return key1;
			}

		public K getNode2()
			{
			return node2;
			}

		public int compareTo(Object o)
			{
			return key1.toString().compareTo(o.toString());
			}

		public String toString()
			{
			return "[" + key1 + ", " + node2 + "]";
			}
		}
	}
