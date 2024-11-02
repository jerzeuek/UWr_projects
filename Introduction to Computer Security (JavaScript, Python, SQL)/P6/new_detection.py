import cv2
import numpy as np

# Load YOLO model
net = cv2.dnn.readNet("D:/uniwerek/WDBK/P6/yolov3.weights", "D:/uniwerek/WDBK/P6/darknet/cfg/yolov3.cfg")

# Start video capture from webcam
cap = cv2.VideoCapture(0)

# Variables to track the first recorded frame when both person and apple are detected
first_frame = None
person_detected = False
apple_detected = False

while True:
    # Capture frame-by-frame
    ret, frame = cap.read()

    # If frame is read correctly, ret is True
    if not ret:
        print("Failed to capture frame")
        break

    # Get image dimensions
    (height, width) = frame.shape[:2]

    # Define the neural network input
    blob = cv2.dnn.blobFromImage(frame, 1 / 255.0, (416, 416), swapRB=True, crop=False)
    net.setInput(blob)

    # Perform forward propagation
    output_layer_name = net.getUnconnectedOutLayersNames()
    output_layers = net.forward(output_layer_name)

    # Initialize lists for bounding boxes, confidences, and class IDs
    boxes = []
    confidences = []
    class_ids = []

    # Loop over the output layers
    for output in output_layers:
        # Loop over the detections
        for detection in output:
            # Extract the class ID and confidence of the current detection
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]

            # Define the classes we're interested in (faces and apples)
            if class_id in [0, 67] and confidence > 0.5:
                # Object detected
                center_x = int(detection[0] * width)
                center_y = int(detection[1] * height)
                w = int(detection[2] * width)
                h = int(detection[3] * height)

                # Rectangle coordinates
                x = int(center_x - w / 2)
                y = int(center_y - h / 2)

                # Add the bounding box, confidence, and class ID to their respective lists
                boxes.append([x, y, w, h])
                confidences.append(float(confidence))
                class_ids.append(class_id)

    # Apply non-maximum suppression to filter out overlapping boxes
    indices = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)

    # Reset detection flags for each frame
    person_detected = False
    apple_detected = False

    # Draw bounding boxes around the filtered detections
    if len(indices) > 0:
        for i in indices.flatten():
            x, y, w, h = boxes[i]
            if class_ids[i] == 0:
                label = "Person"
                color = (0, 255, 0)  # Green for faces
                person_detected = True
            elif class_ids[i] == 67:
                label = "Phone"
                color = (0, 0, 255)
                apple_detected = True
            cv2.rectangle(frame, (x, y), (x + w, y + h), color, 2)
            cv2.putText(frame, label, (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 1, color, 3)

    # Check for person detection
    if person_detected:
        print("Alarm! Person detected!")

    # Check for both person and apple detection
    if person_detected and apple_detected:
        if first_frame is None:
            first_frame = frame.copy()
    
    elif first_frame is not None:
        cv2.destroyWindow("First Recorded Frame")
        first_frame = None

    # Display the resulting frame
    cv2.imshow("Webcam", frame)

    # Display the first recorded frame if both person and apple are detected
    if first_frame is not None:
        cv2.imshow("First Recorded Frame", first_frame)

    # Break the loop on 'q' key press
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything done, release the capture and close windows
cap.release()
cv2.destroyAllWindows()