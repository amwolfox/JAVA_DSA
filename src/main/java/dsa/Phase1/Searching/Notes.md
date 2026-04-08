Welcome to the **Searching** module! In a **Deep Stress** interview, searching isn't just about finding a number in an
array; it's about **reducing the search space** as fast as possible.

The core philosophy of searching is: **"What can I eliminate?"** If you can eliminate half the possibilities at each
step, you move from $O(n)$ (Linear) to $O(\log n)$ (Logarithmic).

---

## The Core Concepts: Deep Stress Style

### 1. Linear Search: The "No-Assumption" Search

The most basic form. You have no information about the data (it's unsorted). You must check every single "room" until
you find the "key."

* **Time:** $O(n)$
* **Interview Trap:** Often used as a baseline. If an interviewer gives you a sorted array and you suggest Linear
  Search, the interview is likely over.

### 2. Binary Search: The "Divide and Conquer" King

This only works on **Sorted Data**. By checking the middle, you discard half the array.

* **The Logic:** If `target > mid`, everything to the left of `mid` is also smaller than the target. Trash it.
* **The "Deep Stress" Nuance:** Binary Search isn't just for arrays. You can Binary Search on **Answers** (like we did
  with `Square Root`). If you can define a range and a "Yes/No" condition, you can Binary Search it.

### 3. Ternary Search: The "Peak" Finder

Instead of one `mid`, you have `mid1` and `mid2` (dividing the range into three parts).

* **Use Case:** Finding the maximum or minimum of a **Unimodal function** (a function that goes up and then down, like a
  hill).
* **Complexity:** $O(\log_3 n)$.

### 4. Meta-Binary Search (One-Sided Binary Search)

Used when the size of the array is unknown or infinite. You jump in powers of 2 ($1, 2, 4, 8, 16\dots$) until you
overshoot the target, then Binary Search back within that range.

---
Prompt:
EXPLAIN THIS IN DEEP STRESS AND SOLUTION with comments TOO AND SOME LEETCODE LINK TO PRACTISE, and also explain the use
case where it can be used

## Top 20 Searching Interview Questions (The Roadmap)

I have categorized these by "Stress Level" so you can build your intuition.

### Level: Foundational (The Basics)

- [x] **1. Binary_Search_Implementation:** Iterative vs Recursive (and why Iterative is usually better for memory).
- [x] **2. Lower_Bound_Upper_Bound:** Find the first or last occurrence of $X$ in a sorted array with duplicates.
- [ ] **3. Search in a 2D Matrix:** Treating a sorted 2D matrix as a long 1D sorted array.

### Level: The "Twist" (Rotated & Infinite)

- [x] **4. Search_in_Rotated_Sorted_Array:** The array was sorted but then shifted (e.g., `[4,5,6,7,0,1,2]`).
- [ ] **5. Find_Minimum_in_Rotated_Sorted_Array:** Find the "pivot" point where the rotation happened.
- [ ] **6. Search in an Infinite Array:** You don't know the `high` index. How do you find it?
- [ ] **7. Find Peak Element:** In an unsorted array, find an element that is greater than its neighbors ($O(\log n)$).

### Level: Binary Search on Answers (Mathematical)

- [ ] **8. Square Root (Integral):** (We covered this—finding $\lfloor\sqrt{x}\rfloor$).
- [ ] **9. Capacity to Ship Packages:** Find the minimum capacity to ship all packages within $D$ days.
- [ ] **10. Koko Eating Bananas:** Find the minimum speed $K$ to finish all bananas within $H$ hours.
- [ ] **11. Aggressive Cows:** Maximize the minimum distance between cows in stalls.
- [ ] **12. Allocate Minimum Number of Pages:** A classic "Hard" problem about dividing work among students.

### Level: Advanced & Hybrid

- [ ] **13. Median of Two Sorted Arrays:** The ultimate "Hard" Binary Search problem ($O(\log(\min(m, n)))$).
- [ ] **14. K-th Smallest Element in a Sorted Matrix:** Using Binary Search on the range of values.
- [ ] **15. Smallest Letter Greater Than Target:** Handling character wrap-around.
- [ ] **16. Find the Duplicate Number:** Using Binary Search on the range $[1, n]$ instead of the array itself.
- [ ] **17. Search in a 2D Matrix II:** A matrix where rows and columns are sorted independently.
- [ ] **18. Single Element in a Sorted Array:** Every element appears twice except one; find it in $O(\log n)$.
- [ ] **19. Missing Element in Sorted Array:** Find the $k$-th missing number.
- [ ] **20. Split Array Largest Sum:** Divide an array into $K$ sub-arrays such that the maximum sum is minimized.

---