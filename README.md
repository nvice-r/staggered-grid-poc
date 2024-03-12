## Staggered Grid POC Project

<img src="/doc/asset/design.png" width="230">
</br></br>

Display a list using `StaggeredGridLayoutManager` with multiple columns, including full-span landscape items.

Enables notifying individual items to perform actions like playing videos.

---
### Finding Optimal Position Algorithm

#### Candidates
The items, which are the first fully displayed on the top of viewport, getting by `findFirstCompletelyVisibleItemPositions` from LayoutManager.

These items will be calculated to find the only optimal one by the algorithm using the above item called **criterian**.

#### Criterian
An selected item to be a criterior for seleting an optimal item from the **cadidates**, getting by the maximum area of the `findFirstVisibleItemPositions` from LayoutManager.

A criterian will be sectored its area, and the number of sectors will equal to `spanCount` of the `RecyclerView`.

For example, if 

<img src="/doc/asset/criterian.svg" width="200">

##### Limitations
- There has some issue the opitmal item position has jump to an unexpected position when spanCount is 3 or greater. 
- A **criterian** will be incorrect in some case. 