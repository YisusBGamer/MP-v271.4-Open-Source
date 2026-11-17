-- Give existing accounts the maximum trunk size; newly created accounts use the same default.
UPDATE trunks
SET slotcount = 128
WHERE slotcount IS NULL OR slotcount <> 128;
