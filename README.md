# Princeton-Percolation
<p><strong>This is my solution to the percolation problem.<strong></p>
<h2>The problem</h2> 
<p>In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1, the system percolates.</p>
<p>When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates, and when p > p*, a random n-by-n grid almost always percolates. No mathematical solution for determining the percolation threshold p* has yet been derived.</p>

<h2>Monte Carlo simulation</h2>
<p>To estimate the percolation threshold, I have used the Monte Carlo simulation</p>
<ul>
  <li>Initialize all sites to be blocked.</li>
  <li>Repeat the following until the system percolates:
    <ul>
      <li>Choose a site uniformly at random among all blocked sites.</li>
      <li>Open the site.</li>
    </ul>
  </li>
  <li>The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.</li>
</ul>

<p>For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.</p>
<p>By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let xt be the fraction of open sites in computational experiment t. The sample mean x⎯⎯⎯x¯ provides an estimate of the percolation threshold; the sample standard deviation s; measures the sharpness of the threshold.</p>


