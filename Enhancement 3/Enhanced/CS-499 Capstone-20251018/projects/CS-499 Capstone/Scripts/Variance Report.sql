SELECT 
    i.ItemID, 
    i.SKU, 
    i.ItemName, 
    v.VariancesBy   AS AdjustedBy, 
    v.VariancesDate AS AdjustmentDate, 
    v.PreviousCount, 
    v.NewCount, 
    (v.NewCount - v.PreviousCount) AS ChangeAmount
FROM Variances v
JOIN Inventory i ON v.ItemID = i.ItemID
WHERE v.VariancesType = 'Manual'
ORDER BY v.VariancesDate DESC;