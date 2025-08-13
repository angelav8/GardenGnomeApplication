  - script: |
      echo "Submitting run for notebook: ${{ parameters.notebookPath }}"
      RUN_ID=$(databricks runs submit \
        --json "{
          \"run_name\": \"ADO Adhoc Run\",
          \"existing_cluster_id\": \"${CLUSTER_ID}\",
          \"notebook_task\": {
            \"notebook_path\": \"${{ parameters.notebookPath }}\"
          }
        }" | jq -r '.run_id')

      echo "Run ID: $RUN_ID"
      echo "##vso[task.setvariable variable=RUN_ID]$RUN_ID"
    displayName: "Submit Databricks Notebook Run"

  - script: |
      echo "Waiting for run $RUN_ID to complete..."
      STATUS="PENDING"
      while [[ "$STATUS" == "PENDING" || "$STATUS" == "RUNNING" ]]; do
        sleep 10
        STATUS=$(databricks runs get --run-id "$RUN_ID" | jq -r '.state.life_cycle_state')
        RESULT=$(databricks runs get --run-id "$RUN_ID" | jq -r '.state.result_state')
        echo "Current status: $STATUS ($RESULT)"
      done

      if [[ "$RESULT" != "SUCCESS" ]]; then
        echo "Run failed with status: $RESULT"
        exit 1
      else
        echo "Run completed successfully."
      fi
    env:
      RUN_ID: $(RUN_ID)
    displayName: "Wait for Completion and Fail if Needed"
