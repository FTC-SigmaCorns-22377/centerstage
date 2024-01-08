package org.firstinspires.ftc.teamcode.Utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class LimitedSizeQueue<K> extends ArrayList<K> {

	private final int maxSize;

	public LimitedSizeQueue(int size) {
		this.maxSize = size;
	}

	public boolean add(K k) {
		boolean r = super.add(k);
		if (size() > maxSize) {
			while (size() > maxSize) {
				this.remove(getYoungest());
			}
		}
		return r;
	}

	public K getYoungest() {
		return get(size() - 1);
	}

	public K getOldest() {
		return get(0);
	}

	@NonNull
	public Double[] toArray() {
		Double[] objArray = new Double[this.size()];
		for (int i = 0; i < this.size(); i++) {
			objArray[i] = (Double) this.get(i);
		}
		return objArray;
	}

	@NonNull
	@Override
	public String toString() {
		StringBuilder BaseString = new StringBuilder("limited size Queue: ");
		for (Object o : this) {
			BaseString.append(o);
		}
		return BaseString.toString();


	}
}