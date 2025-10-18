SELECT 
    o.SKU, 
    COUNT(*) AS TotalReturns, 
    ROUND(
        (COUNT(*) * 100.0 / 
        (SELECT COUNT(*)
         FROM Orders o2
         JOIN Returns r2 ON o2.OrderID = r2.OrderID
         WHERE UPPER(o2.Status) = 'COMPLETE')), 2
    ) AS ReturnPercentage
FROM Orders o
JOIN Returns r ON o.OrderID = r.OrderID
WHERE UPPER(o.Status) = 'COMPLETE'
GROUP BY o.SKU
ORDER BY ReturnPercentage DESC;