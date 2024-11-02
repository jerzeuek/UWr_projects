import json
from flask import Flask, request, jsonify

@app.route('/osoba/<int:ide>', methods=['GET'])
def get_osoba(ide):
    print(ide)
    return jsonify
