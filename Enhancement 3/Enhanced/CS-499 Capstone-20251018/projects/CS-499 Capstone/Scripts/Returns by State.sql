SELECT 
    c.State AS State,
    COUNT(*) AS TotalReturns
FROM Returns r
JOIN Orders  o ON r.OrderID = o.OrderID
JOIN Customer c ON o.CustomerID = c.CustomerID
GROUP BY c.State
ORDER BY TotalReturns DESC;
