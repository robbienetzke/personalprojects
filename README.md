# Hi

You are viewing a repository of some of my favorite projects that I have taken on. Some have been given to me as problems in an academic setting. These programming projects, analyses and proofs can be found in the schoolprojects directory. Outside of academics, I have taken an interest in algorithmic stock trading. These algorithms may be found in stocktradingalgos.

# stocktradingalgos

### pairstrading.py

This algorithm uses a basket of cointegrated stock pairs and trades each pair based on the price spread between the two stocks. In prior research, I verified the cointegration of stock pairs using an Augmented Dickey-Fuller (ADF) test. Stationarity in the price spread allows for a mean-reversion assumption, so this algorithm longs and shorts a stock in each pair depending on the spread of their prices. Each asset is operates under the Morningstar classification of a semiconductor firm, so the price correlation between any two assets in this particular basket typically have a correlation coefficient above 0.5. The basket of stocks does not attempt to minimize variance of returns by choosing uncorrelated pairs, which makes it not practial for use; However, the logic of the algorithm may be applied to any number of uncorrelated pairs and the stock universe may be manually adjusted. That being said, if there is another Y2K, the whole portfolio would likely suffer. Unfortunately, Quantopian has dispanded, the web server is completely lost, and this algorithm used the Quantopian classes and IDE.

# schoolprojects

### Algoritms

In here, I implement a connected graph shortest path solver using A*. The script can solve a variety of problems seemingly unrelated to A*, just as long as the problem is presented as a shortest paths problem on a graph with positive weighted edges. In the worst case, A* has a linear runtime. Other algorithms were implemented outside the IDE for this course (midterms, final).

### Data Structures 

In here, I have a variety of data structures to explore. Notable ones are: ArrayList, which is an ArrayList that uses a Java Array as the underlying structure (not resizeable), but the ArrayList implemented is resizable. ArrayHeapMinPQ, which is a priority queue with the ability to change the priority of an item. KDTree, which is a two dimensional tree that allows for quick access, on average, of nodes in the tree. 

### Econometric Analysis

In this homework, I explored the distribution of firm productivity under the Cobb-Douglas production function. I proved that inherit firm productivity does not depend on each year in formal proofs, and I explored the distributions of productivity in 2013 and 2014. The Olley-Pakes algorithm is a great algorithm to solve for firm productivity given values for inputs, and I have implemented it within this analysis. The results found are interesting to any applied economist.

### Time Series Analysis

In this project, I explore a variety of Autoregressive Moving Average models to fit to a dataset of number of acres burned in a county over a few decades. Different models are considered, and one final model is explored in further detail at the end of the report, along with a graph of my predictions for the coming decade.

