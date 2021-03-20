#version 330 core

in vec3 pos;
in vec2 texcoord;

out vec2 textureCoord;

uniform float scale;
uniform mat4 projection;

void main()
{
	textureCoord = texcoord;

	gl_Position = projection * vec4(scale, scale, scale, 1.0) * vec4(pos, 1.0);
}