#!/usr/bin/python3
import json
import requests
import os
import sys
import time

def main():
  shard = ''
  token = ''
  clusterid = ''
  workspacepath = ''
  outfilepath = ''

  if len(sys.argv) < 2:
    print('Usage: executenotebook.py <localpath>')
    sys.exit(2)

  localpath = sys.argv[1]  # <-- Take file/folder path directly from argument

  # hardcode or set these from environment variables / pipeline vars
  shard = os.environ.get("SHARD", "")
  token = os.environ.get("TOKEN", "")
  clusterid = os.environ.get("CLUSTERID", "")
  workspacepath = os.environ.get("WORKSPACEPATH", "")
  outfilepath = os.environ.get("OUTFILEPATH", "")

  print('-s is ' + shard)
  print('-t is ' + token)
  print('-c is ' + clusterid)
  print('-l is ' + localpath)
  print('-w is ' + workspacepath)
  print('-o is ' + outfilepath)

  notebooks = []
  for path, subdirs, files in os.walk(localpath):
    for name in files:
      fullpath = path + '/' + name
      fullworkspacepath = workspacepath + path.replace(localpath, '')

      name, file_extension = os.path.splitext(fullpath)
      if file_extension.lower() in ['.scala', '.sql', '.r', '.py']:
        notebooks.append([fullpath, fullworkspacepath, 1])

  for notebook in notebooks:
    nameonly = os.path.basename(notebook[0])
    workspacepath_nb = notebook[1]

    name, file_extension = os.path.splitext(nameonly)
    fullworkspacepath = workspacepath_nb + '/' + name + file_extension

    print('Running job for: ' + fullworkspacepath)
    values = {
      'run_name': name,
      'existing_cluster_id': clusterid,
      'timeout_seconds': 3600,
      'notebook_task': {'notebook_path': fullworkspacepath}
    }

    resp = requests.post(shard + '/api/2.0/jobs/runs/submit',
      data=json.dumps(values), auth=("token", token))
    runjson = resp.text
    print("runjson: " + runjson)
    d = json.loads(runjson)
    runid = d['run_id']

    i=0
    while True:
      time.sleep(10)
      jobresp = requests.get(shard + '/api/2.0/jobs/runs/get?run_id='+str(runid),
        data=json.dumps(values), auth=("token", token))
      jobjson = jobresp.text
      print("jobjson: " + jobjson)
      j = json.loads(jobjson)
      current_state = j['state']['life_cycle_state']
      if current_state in ['TERMINATED', 'INTERNAL_ERROR', 'SKIPPED'] or i >= 12:
        break
      i+=1

    if outfilepath:
      with open(outfilepath + '/' +  str(runid) + '.json', 'w') as file:
        file.write(json.dumps(j))

if __name__ == '__main__':
  main()
