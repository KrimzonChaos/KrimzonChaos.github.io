SELECT 
    r.Reason AS ReturnReason, 
    COUNT(*) AS TotalReturns, 
    ROUND(
        (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Returns)), 2
    ) AS PercentageOfTotal
FROM Returns r
GROUP BY r.Reason
ORDER BY TotalReturns DESC;