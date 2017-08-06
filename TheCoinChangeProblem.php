// https://www.hackerrank.com/challenges/coin-change?h_r=internal-search

<?php

$handle = fopen ("php://stdin", "r");
function getWays($n, $c) {
    if ($n < 0)
        return 0;
    if ($n == 0)
        return 1;
    $swapped = false;
    $p = 0;
    do {
        $swapped = false;
        $p++;
        for ($i = 0; $i < count($c) - $p; $i++)
            if ($c[$i] > $c[$i + 1]) {
                $swapped = true;
                $temp = $c[$i];
                $c[$i] = $c[$i + 1];
                $c[$i + 1] = $temp;
            }
    } while ($swapped);
    if ($n == $c[0])
        return 1;
    $numWays = 0;
    for ($a = 0; $a < count($c); $a++) {
        $v = array();
        for ($u = 0; $u < count($c) - $a; $u++)
            $v[] = $c[$u + $a];
        $numWays += getWays($n - $c[$a], $v);
    }
    return $numWays;
}

fscanf($handle, "%d %d", $n, $m);
$c_temp = fgets($handle);
$c = explode(" ",$c_temp);
$c = array_map('intval', $c);
// Print the number of ways of making change for 'n' units using coins having the values given by 'c'
$ways = getWays($n, $c);
print("$ways");
?>
